package controller.struts.action.main.helpInfos;

import common.beanFactory.BeanFactoryProvider;
import controller.struts.action.common.CustomResults;
import controller.struts.action.common.PrevActionMsgHandlingExceptionsAction;
import dataAccess.common.Node;
import domain.HelpInfo;
import domain.HelpInfo_;
import domain.IssueType;
import domain.IssueType_;
import domain.common.SimpleFinder;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Артем on 25.03.2018.
 */
public class HelpInfoManagementAction extends PrevActionMsgHandlingExceptionsAction {

    protected static final Logger LOG = LogManager.getLogger(HelpInfoManagementAction.class);

    protected HelpInfo helpInfo=new HelpInfo();
    protected long issueTypeId;

    @Override
    protected String doExecute() throws Exception {
        SecuritySubjectUtils.checkPermission("helpinfo:edit,view:*:"+ issueTypeId);
        addPrevActionMsgAndErrorToAction();
        SimpleFinder<HelpInfo,Long> helpInfosFinder = (SimpleFinder<HelpInfo,Long>)BeanFactoryProvider.getBeanFactory().getBean("helpInfosFinder");
        helpInfo=helpInfosFinder.findOne(issueTypeId,null,
                Node.rootNode().addChild(new Node<>(HelpInfo_.issueType.getName()).addChild(IssueType_.issueCategory.getName())));
        return CustomResults.SUCCESS;
    }

//    public Map<String,String> findAllDefaultCausesOfTransition(){
//        DefaultCauseOfTransitionToCanceledState[] defaultCausesOfTransitionsList = DefaultCauseOfTransitionToCanceledState.values();
//        HashMap<String, String> result = new HashMap<>();
//        for (DefaultCauseOfTransitionToCanceledState cause : defaultCausesOfTransitionsList) {
//            result.put(cause.name(), getText(cause.name()));
//        }
//        return result;
//    }


    public HelpInfo getHelpInfo() {
        return helpInfo;
    }

    public long getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(long issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
