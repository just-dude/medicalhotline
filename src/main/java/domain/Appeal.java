package domain;

import common.DateTimeUtils;
import common.argumentAssert.Assert;
import dataAccess.common.Node;
import domain.common.HavingLongIdAccountableDomainObject;
import domain.common.exception.*;
import domain.common.stateChangesAccounting.EntityPrevStateDiffBuilder;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Appeals")
public class Appeal  extends HavingLongIdAccountableDomainObject<Appeal> {

    public static final String ISSUES = "issues";

    private String contactPhoneNumber;

    public enum State{
        NotFilled, Filling, OnTheGo,
        //final states
        PartiallyResolved,Resolved,ImpossibleToSolve,Canceled
    }
    @Enumerated(EnumType.STRING)
    private State state;
    private String causeOfTransitionToFinalState;

    @Embedded
    private Citizen citizen;

    @OneToMany(targetEntity = Issue.class,mappedBy = "appeal")
    private List<Issue> issues = new ArrayList<>();


    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.EAGER,optional = false,targetEntity = UserAccount.class)
    @JoinColumn(name = "creatorId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private UserAccount creator;


    public Appeal() {
    }

    public Appeal(Long id) {
        super(id);
    }

    public Appeal(Long id, String contactPhoneNumber, Citizen citizen) {
        super(id);
        this.contactPhoneNumber = contactPhoneNumber;
        this.citizen = citizen;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getCreatedDateTimeFormattedString() {
        if(createdDateTime==null)
            return null;
        return DateTimeUtils.toString(createdDateTime);
    }

    private void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public void setCreator(UserAccount creator) {
        this.creator = creator;
    }

    public State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    public String getCauseOfTransitionToFinalState() {
        return causeOfTransitionToFinalState;
    }

    public void setCauseOfTransitionToFinalState(String causeOfTransitionToFinalState) {
        this.causeOfTransitionToFinalState = causeOfTransitionToFinalState;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    @Transactional
    protected void notifyAboutOneOfIssuesInFinalState(){
        if(getState().equals(State.OnTheGo)) {
            goToFinalStateIfAllIssuesIsInFinalStateAndSave();
        }
    }

    private void goToFinalStateIfAllIssuesIsInFinalState(){
        if(getIssues().size()==0){
            return;
        }
        boolean isAllIssuesIsInFinalState=true;
        boolean hasIssueWithImpossibleToSolveState=false;
        boolean isAllIssuesIsInImpossibleToSolveState=true;
        boolean isAllIssuesIsInNotRelevantState=true;
        for(Issue issue: getIssues()){
            if(issue.getState().equals(Issue.State.ImpossibleToSolve)){
                hasIssueWithImpossibleToSolveState=true;
            } else{
                isAllIssuesIsInImpossibleToSolveState=false;
            }
            if(!issue.getState().equals(Issue.State.NotRelevant)){
                isAllIssuesIsInNotRelevantState=false;
            }
            if(!issue.isInFinalState()){
                isAllIssuesIsInFinalState=false;
                break;
            }
        }
        if(isAllIssuesIsInFinalState){
            if(isAllIssuesIsInImpossibleToSolveState || isAllIssuesIsInNotRelevantState){
                setState(State.ImpossibleToSolve);
            } else if(hasIssueWithImpossibleToSolveState){
                setState(State.PartiallyResolved);
            } else{
                setState(State.Resolved);
            }
        }
    }

    @Transactional
    private void goToFinalStateIfAllIssuesIsInFinalStateAndSave(){
        State stateBefore = getState();
        goToFinalStateIfAllIssuesIsInFinalState();
        if(!getState().equals(stateBefore)){
            doSave();
        }
    }


    protected void notifyAboutOneOfIssuesInOnTheGoStateAgain(){
        if(getState().equals(State.ImpossibleToSolve) || getState().equals(State.PartiallyResolved) || getState().equals(State.Resolved)) {
            setState(State.OnTheGo);
            setCauseOfTransitionToFinalState(null);
            doSave();
        }
    }

    private void goToOnTheGoState(){
        if(!isAllowedToGoToTheOnTheGoState()){
            throw new RuleViolationBusinessException("Not allowed to go appeal to the 'OnTheGo' state when appeal not in the state 'Filling'","not.allowed.to.go.appeal.to.the.OnTheGo.state");
        }
        setState(State.OnTheGo);
        setCauseOfTransitionToFinalState(null);
    }

    @Transactional
    public void goToOnTheGoStateAndSave(){
        Appeal self=findNotSoftDeletedSelf();
        self.goToOnTheGoState();
        self.goToFinalStateIfAllIssuesIsInFinalState();
        self.doSave();
    }

    private void goToCanceledState(String causeOfTransition){
        if(!isAllowedToGoToTheCanceledState()){
            throw new RuleViolationBusinessException("Not allowed to go appeal to the 'Canceled' state when appeal in the state 'Canceled' or has issues","not.allowed.to.go.appeal.to.the.Canceled.state");
        }
        setState(State.Canceled);
        setCauseOfTransitionToFinalState(causeOfTransition);
    }

    @Transactional
    public void goToCanceledStateAndSave(String causeOfTransition){
        Appeal self=findNotSoftDeletedSelf();
        self.goToCanceledState(causeOfTransition);
        self.doSave();
    }

    @Transactional
    public Issue addIssue(Issue issue){
        Appeal self=findNotSoftDeletedSelf();
        if(!self.isAllowedToAddIssues()){
            throw new RuleViolationBusinessException("Not allowed to add issuees some time after appeal created time","not.allowed.to.add.issues.some.time.after.appeal.created.datetime");
        }
        issue.setAppeal(self);
        return issue.save();
    }

    @Override
    protected void beforeInsert() {
        super.beforeInsert();
        setCreatedDateTime(DateTimeUtils.now());
        setCreator(SecuritySubjectUtils.getCurrentUserAccount());
        setState(State.NotFilled);
    }

    @Override
    protected void beforeUpdate(Appeal self) {
        super.beforeUpdate(self);
        if(!self.isAllowedToEdit()){
            throw new RuleViolationBusinessException("Not allowed to edit appeal when appeal in one of final states","not.allowed.to.edit.appeal");
        }
    }

    @Override
    protected void fillSelfForUpdate(Appeal self) {
        super.fillSelfForUpdate(self);
        self.setContactPhoneNumber(getContactPhoneNumber());
        if(self.getCitizen()==null || !self.getCitizen().isFilled()){
            self.setState(State.Filling);
        }
        self.setCitizen(getCitizen());
    }

    @Transactional(readOnly = true)
    public boolean isAllowedToAddIssues(){
        Assert.notNull(getState(),"state");
        return getState().equals(State.Filling);// || getState().equals(State.OnTheGo)) && DateTimeUtils.now().isBefore(getCreatedDateTime().plusHours(1));
    }

    @Transactional(readOnly = true)
    public boolean isAllowedToEdit(){
        Assert.notNull(getState(),"state");
        return !getState().equals(State.Canceled) && !getState().equals(State.ImpossibleToSolve) && !getState().equals(State.PartiallyResolved)
                && !getState().equals(State.Resolved);
    }

//    @Transactional(readOnly = true)
//    protected boolean isAllowedToAddIssueActions(){
//        Assert.notNull(getState(),"state");
//        return !getState().equals(State.Canceled) && !getState().equals(State.ImpossibleToSolve);
//    }

    @Transactional(readOnly = true)
    public boolean isAllowedToGoToTheCanceledState(){
        Assert.notNull(getState(),"state");
        return !getState().equals(State.Canceled) && getIssues().size()==0;
    }

    @Transactional(readOnly = true)
    public boolean isAllowedToGoToTheOnTheGoState(){
        Assert.notNull(getState(),"state");
        return getState().equals(State.Filling) &&  getIssues().size()>0;
    }

    @Override
    protected void doInitializeProperties(Node<String> initTree) {
        if(initTree.containsData(Appeal.ISSUES)){
            getIssues().size();
        }
        if(initTree.containsData(Issue.ISSUE_TYPES) || initTree.containsData(Issue.TAKEN_ACTIONS) || initTree.containsData(Issue.ORGANIZATIONS)){
            for(Issue issue:getIssues()){
                issue.initializeProperties(initTree);
            }
        }
    }

    @Override
    protected void fillEntityPrevStateDiffBuilder(EntityPrevStateDiffBuilder builder, Appeal prev, Appeal current) {
        if (!prev.getContactPhoneNumber().equals(current.getContactPhoneNumber())) {
            builder.addField("contactPhoneNumber", prev.getContactPhoneNumber());
        }
        if(prev.getCitizen()!=null) {
            if (!prev.getCitizen().getName().equals(current.getCitizen().getName())){
                builder.addField("citizenName", prev.getCitizen().getName());
            }
            if (!prev.getCitizen().getSurname().equals(current.getCitizen().getSurname())){
                builder.addField("citizenSurname", prev.getCitizen().getSurname());
            }
            if (prev.getCitizen().getPatronymic()!=null && !prev.getCitizen().getPatronymic().equals(current.getCitizen().getPatronymic())){
                builder.addField("citizenPatronymic", prev.getCitizen().getPatronymic());
            } else if(prev.getCitizen().getPatronymic()==null && current.getCitizen().getPatronymic()!=null){
                builder.addField("citizenPatronymic", "");
            }
            if (prev.getCitizen().getOmsPolicyNumber()!=null && !prev.getCitizen().getOmsPolicyNumber().equals(current.getCitizen().getOmsPolicyNumber())){
                builder.addField("citizenOmsPolicyNumber", prev.getCitizen().getOmsPolicyNumber());
            } else if(prev.getCitizen().getOmsPolicyNumber()==null && current.getCitizen().getOmsPolicyNumber()!=null){
                builder.addField("citizenOmsPolicyNumber", "");
            }
            if (prev.getCitizen().getSmo()!=null && !prev.getCitizen().getSmo().equals(current.getCitizen().getSmo())){
                builder.addField("citizenSmo", prev.getCitizen().getSmo().getName());
            } else if(prev.getCitizen().getSmo()==null && current.getCitizen().getSmo()!=null){
                builder.addField("citizenSmo", "");
            }
            if (prev.getCitizen().getPlaceOfLiving()!=null && !prev.getCitizen().getPlaceOfLiving().equals(current.getCitizen().getPlaceOfLiving())){
                builder.addField("citizenPlaceOfLiving", prev.getCitizen().getPlaceOfLiving());
            } else if(prev.getCitizen().getPlaceOfLiving()==null && current.getCitizen().getPlaceOfLiving()!=null){
                builder.addField("citizenPlaceOfLiving", "");
            }
        }
    }

    @Override
    public String toString() {
        return "Appeal{" +
                "id=" + id +
                ", contactPhoneNumber=" + contactPhoneNumber +
                ", citizen=" + citizen +
                ", causeOfTransitionToFinalState=" + causeOfTransitionToFinalState +
                ", createdDateTime=" + createdDateTime +
                ", creator=" + creator +
                '}';
    }
}
