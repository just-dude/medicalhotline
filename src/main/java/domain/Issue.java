package domain;

import common.argumentAssert.Assert;
import dataAccess.common.Node;
import domain.common.HavingLongIdAccountableDomainObject;
import domain.common.exception.RuleViolationBusinessException;
import domain.common.stateChangesAccounting.EntityPrevStateDiffBuilder;
import domain.common.util.DomainObjectUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Артем on 18.03.2018.
 */

@Entity
@Table(name = "Issues")
public class Issue extends HavingLongIdAccountableDomainObject<Issue> {

    public static final String TAKEN_ACTIONS = "takenActions";
    public static final String ORGANIZATIONS = "organizations";
    public static final String ISSUE_TYPES = "issueTypes";

    public enum State {
        PrimaryProcessing, OnTheGo,
        //final states
        ImpossibleToSolve, NotRelevant, Resolved
    }

    @Lob
    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "appealId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private Appeal appeal;

    @ManyToMany(targetEntity = Organization.class)
    @JoinTable(name = "Issues_Organizations", joinColumns = @JoinColumn(name = "issueId"), inverseJoinColumns = @JoinColumn(name = "organizationId"))
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(value = "id")
    private Set<Organization> organizations = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "issueCategoryId", referencedColumnName = "id" ,nullable = false)
    @Fetch(FetchMode.JOIN)
    private IssueCategory issueCategory;

    @ManyToMany(targetEntity = IssueType.class,fetch = FetchType.LAZY)
    @JoinTable(name = "Issues_IssueTypes", joinColumns = @JoinColumn(name = "issueId"), inverseJoinColumns = @JoinColumn(name = "issueTypeId"))
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy(value = "id")
    private Set<IssueType> issueTypes = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private State state;
    private String causeOfTransitionToFinalState;

    @OneToMany(targetEntity = IssueAction.class, mappedBy = "issue")
    @Fetch(FetchMode.SUBSELECT)
    private List<IssueAction> takenActions = new ArrayList<>();


    public Issue() {
    }

    public Issue(Long id) {
        super(id);
    }

    public Issue(Long id, String text, Appeal appeal,IssueCategory issueCategory) {
        super(id);
        this.text = text;
        this.appeal = appeal;
        this.issueCategory = issueCategory;
    }

    public Issue(Long id, String text) {
        super(id);
        this.text = text;
    }

    public Issue(String text) {
        super(null);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCauseOfTransitionToFinalState() {
        return causeOfTransitionToFinalState;
    }

    public void setCauseOfTransitionToFinalState(String causeOfTransitionToFinalState) {
        this.causeOfTransitionToFinalState = causeOfTransitionToFinalState;
    }

    public IssueCategory getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(IssueCategory issueCategory) {
        this.issueCategory = issueCategory;
    }

    public void addLabel(IssueType issueType) {
        issueTypes.add(issueType);
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    public Appeal getAppeal() {
        return appeal;
    }

    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
    }

    public Set<IssueType> getIssueTypes() {
        return issueTypes;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public List<IssueAction> getTakenActions() {
        return takenActions;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }

    public void setIssueTypes(Set<IssueType> issueTypes) {
        this.issueTypes = issueTypes;
    }

    @Transactional
    public IssueAction addAction(IssueAction issueAction) {
        Issue self=findNotSoftDeletedSelf();
        if (!self.isAllowedToAddActions()) {
            throw new RuleViolationBusinessException("Not allowed to add taken actions to issue", "not.allowed.to.add.actions");
        }
        issueAction.setIssue(self);
        IssueAction savedIssueAction=issueAction.save();
        if(!self.getTakenActions().contains(savedIssueAction)) {
            self.getTakenActions().add(savedIssueAction);
        }
        self.goToAnotherStateIfItNeedAndSave();
        return savedIssueAction;
    }

    @Transactional
    private void goToAnotherStateIfItNeedAndSave(){
        Issue.State newStateForTransition = null;
        List<IssueAction> issueActions = getTakenActions();
        if (issueActions.size() == 1) {
            newStateForTransition = calculateNewStateForTransitionByTwoLastActions(null, issueActions.get(0));
        } else {
            newStateForTransition = calculateNewStateForTransitionByTwoLastActions(issueActions.get(issueActions.size() - 2), issueActions.get(issueActions.size() - 1));
        }
        if (newStateForTransition != null) {
            if(isInFinalState() && newStateForTransition.equals(State.OnTheGo)){
                getAppeal().notifyAboutOneOfIssuesInOnTheGoStateAgain();
            }
            setState(newStateForTransition);
            doSave();
            if(isInFinalState()){
                getAppeal().notifyAboutOneOfIssuesInFinalState();
            }
        }
    }

    private Issue.State calculateNewStateForTransitionByTwoLastActions(IssueAction penultimate, IssueAction last) {
        // penultimate action may be null if issue has only one action
        if ((penultimate == null || (penultimate != null && penultimate.isFinal())) && !last.isFinal()) {
            return State.OnTheGo;
        }
        if ((penultimate == null || (penultimate != null && !penultimate.isFinal())) && last.isFinal()) {
            return State.Resolved;
        }
        return null;
    }

    public boolean isInFinalState() {
        Assert.notNull(getState(),"state");
        return (getState().equals(State.Resolved) || getState().equals(State.ImpossibleToSolve) || getState().equals(State.NotRelevant));
    }

    @Transactional(readOnly = true)
    public boolean isAllowedToAddActions() {
        Assert.notNull(getState(),"state");
        return (!getState().equals(State.NotRelevant) && !getState().equals(State.ImpossibleToSolve));// && getAppeal().isAllowedToAddIssueActions();
    }

    @Transactional(readOnly = true)
    public boolean isAllowedToEditActions() {
        Assert.notNull(getState(),"state");
        return !isInFinalState();
    }

    public boolean isAllowedToGoToImpossibleToSolveState() {
        return !isInFinalState();
    }

    public boolean isAllowedToGoToNotRelevantState() {
        return !isInFinalState();
    }

    @Transactional
    public void goToImpossibleToSolveStateAndSave(String causeOfTransition){
        Issue self=findNotSoftDeletedSelf();
        self.goToImpossibleToSolveState(causeOfTransition);
        self.doSave();
        self.getAppeal().notifyAboutOneOfIssuesInFinalState();
    }

    private void goToImpossibleToSolveState(String causeOfTransition){
        if(isAllowedToGoToImpossibleToSolveState()) {
            setState(State.ImpossibleToSolve);
            setCauseOfTransitionToFinalState(causeOfTransition);
        }
    }

    @Transactional
    public void goToNotRelevantStateAndSave(String causeOfTransition){
        Issue self=findNotSoftDeletedSelf();
        self.goToNotRelevantState(causeOfTransition);
        self.doSave();
        self.getAppeal().notifyAboutOneOfIssuesInFinalState();
    }

    private void goToNotRelevantState(String causeOfTransition){
        if(isAllowedToGoToNotRelevantState()) {
            setState(State.NotRelevant);
            setCauseOfTransitionToFinalState(causeOfTransition);
        }
    }

    //@Transactional(readOnly = true)
    public boolean isAllowedToEdit(){
        Assert.notNull(getState(),"state");
        return !getState().equals(State.ImpossibleToSolve) && !getState().equals(State.NotRelevant) && !getState().equals(State.Resolved);
    }


    @Override
    protected void beforeInsert() {
        super.beforeInsert();
        setState(State.PrimaryProcessing);
    }

    @Override
    protected void beforeUpdate(Issue self) {
        if(!self.isAllowedToEdit()){
            throw new RuleViolationBusinessException("Not allowed to edit issue when it in one of final states","not.allowed.to.edit.issue");
        }
        super.beforeUpdate(self);
    }

    @Override
    protected void fillSelfForUpdate(Issue self) {
        super.fillSelfForUpdate(self);
        self.setIssueCategory(getIssueCategory());
        self.setText(getText());
        self.setIssueTypes(getIssueTypes());
        self.setOrganizations(getOrganizations());
    }

    @Override
    protected void fillEntityPrevStateDiffBuilder(EntityPrevStateDiffBuilder builder, Issue prev, Issue current) {
        if(!prev.getText().equals(current.getText())){
            builder.addField("text",prev.getText());
        }
        if(!prev.getIssueCategory().getId().equals(current.getIssueCategory().getId())){
            builder.addField("issueCategory",prev.getIssueCategory().getName());
        }
        if(!DomainObjectUtils.isTwoSetsHasEqualDomainObjectsIds(prev.getIssueTypes(),current.getIssueTypes())){
            builder.addField("issueTypes",DomainObjectUtils.domainObjectsSetToNamesString(prev.getIssueTypes()));
        }
        if(!DomainObjectUtils.isTwoSetsHasEqualDomainObjectsIds(prev.getOrganizations(),current.getOrganizations())){
            builder.addField("organizations",DomainObjectUtils.domainObjectsSetToNamesString(prev.getOrganizations()));
        }
    }

    @Override
    protected void doInitializeProperties(Node<String> initTree) {
        if(initTree.containsData(Issue.ISSUE_TYPES)){
            getIssueTypes().size();
        }
        if(initTree.containsData(Issue.ORGANIZATIONS)){
            getOrganizations().size();
        }
        if(initTree.containsData(Issue.TAKEN_ACTIONS)){
            getTakenActions().size();
        }
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", appealId=" + ((appeal != null) ? appeal.getId() : "null") +
                ", state=" + state +
                ", causeOfTransitionToFinalState=" + causeOfTransitionToFinalState +
                '}';
    }
}
