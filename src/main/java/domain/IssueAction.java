package domain;

import common.DateTimeUtils;
import common.beanFactory.BeanFactoryProvider;
import domain.common.HavingLongIdAccountableDomainObject;
import domain.common.exception.RuleViolationBusinessException;
import domain.common.notifications.NotificationContent;
import domain.common.notifications.NotificationsService;
import domain.common.stateChangesAccounting.EntityPrevStateDiffBuilder;
import domain.security.SecuritySubjectUtils;
import domain.userAccounts.UserAccount;
import domain.userAccounts.UserGroup;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Артем on 18.03.2018.
 */
@Entity
@Table(name = "IssueActions")
public class IssueAction extends HavingLongIdAccountableDomainObject<IssueAction> {

    @Lob
    @Column
    private String text;
    private boolean isFinal=false;
    @ManyToOne
    @JoinColumn(name = "creatorId", referencedColumnName = "id" ,nullable = false)
    private UserAccount creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issueId", referencedColumnName = "id" ,nullable = false)
    private Issue issue;

    private LocalDateTime createdDateTime;

    public IssueAction() {
    }

    public IssueAction(Long id) {
        super(id);
    }

    public IssueAction(String text, boolean isFinal) {
        super(null);
        this.text = text;
        this.isFinal = isFinal;
    }

    public IssueAction(Long id, String text, boolean isFinal, UserAccount creator,Issue issue) {
        super(id);
        this.text = text;
        this.isFinal = isFinal;
        this.creator = creator;
        this.issue=issue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean getIsFinal() {return isFinal; }

    public void setIsFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public void setCreator(UserAccount creator) {
        this.creator = creator;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    private void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCreatedDateTimeFormattedString() {
        if(createdDateTime==null)
            return null;
        return DateTimeUtils.toString(createdDateTime);
    }

    @Override
    protected void beforeInsert() {
        super.beforeInsert();
        setCreatedDateTime(DateTimeUtils.now());
        setCreator(SecuritySubjectUtils.getCurrentUserAccount());
    }

    @Override
    protected void afterInsert(IssueAction self) {
        super.afterInsert(self);
        if(SecuritySubjectUtils.getCurrentUserAccount().getUserGroup().equals(UserGroup.ResponsiblePerson)) {
            String issueText=self.getIssue().getText();
            if(issueText.length()>80){
                issueText=issueText.substring(0,81)+"...";
            }
            NotificationContent notificationContent = new NotificationContent(
                    "issueActionAddedToIssueByResponsiblePersonText|"+SecuritySubjectUtils.getCurrentUserAccount().getProfile().getFullName()+"|"+issueText
                    ,"issueActionAddedToIssueByResponsiblePersonLink|"+self.getId()+"|"+self.getIssue().getId()+"|"+self.getIssue().getAppeal().getId());
            NotificationsService notificationsService = (NotificationsService) BeanFactoryProvider.getBeanFactory().getBean("notificationsService");
            notificationsService.addForUserAccountsByGroup(notificationContent, new UserGroup[]{UserGroup.Operator, UserGroup.ShiftSupervisor,UserGroup.Admin});
        }
    }

    @Override
    protected void beforeUpdate(IssueAction self) {
        if(!self.isAllowedToEdit()){
            throw new RuleViolationBusinessException("Not allowed to edit issueAction when issue in one of final states","not.allowed.to.edit.issueAction");
        }
        super.beforeUpdate(self);
    }

    @Override
    protected void fillSelfForUpdate(IssueAction self) {
        self.setText(getText());
    }

    @Override
    protected void fillEntityPrevStateDiffBuilder(EntityPrevStateDiffBuilder builder, IssueAction prev, IssueAction current) {
        if(!prev.getText().equals(current.getText())){
            builder.addField("text",prev.getText());
        }
//        if(prev.getIsFinal()!=current.getIsFinal()){
//            builder.addField("isFinal",Boolean.toString(prev.getIsFinal()));
//        }
    }

    public boolean isAllowedToEdit(){
        return getIssue().isAllowedToEdit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IssueAction)) return false;
        IssueAction that = (IssueAction) o;
        return isFinal() == that.isFinal() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getText(), that.getText()) &&
                Objects.equals(getCreator(), that.getCreator()) &&
                Objects.equals(getIssue(), that.getIssue()) &&
                Objects.equals(getCreatedDateTime(), that.getCreatedDateTime());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(),getText(), isFinal(), getCreator(), getIssue(), getCreatedDateTime());
    }

    @Override
    public String toString() {
        return "IssueAction{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isFinal=" + isFinal +
                ", creator=" + creator +
                ", issueId=" + ((issue!=null)?issue.getId():"null") +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
