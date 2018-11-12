package epam.core.services.workflow;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.mailer.MailingException;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.HashMap;
import java.util.Map;

@Component(immediate = true, service = WorkflowProcess.class)
public class SendEmailToInisiator implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(SendEmailToInisiator.class);
    private static final String EXCEPTION_ERROR = "Error! Something went wrong: ";
    private static final String MAIL_ERROR = "Error! Could not send mail: ";
    private static final String PROFILE_EMAIL = "profile/email";

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private MessageGatewayService messageGatewayService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        Map<String, Object> param = new HashMap<>();
        param.put(ResourceResolverFactory.USER, "admin");
        param.put(ResourceResolverFactory.PASSWORD, "admin".toCharArray());

        try {
            ResourceResolver resourceResolver = resolverFactory.getResourceResolver(param);
            UserManager manager = resourceResolver.adaptTo(UserManager.class);
            Authorizable authorizable = manager.getAuthorizable(workItem.getWorkflow().getInitiator());
            String userEmail = PropertiesUtil.toString(authorizable.getProperty(PROFILE_EMAIL), "");
            String pagePath = workItem.getWorkflowData().getPayload().toString();

            MessageGateway<Email> messageGateway;
            Email email = new SimpleEmail();

            email.setCharset(CharEncoding.UTF_8);
            email.addTo(userEmail);
            email.setSubject("Workflow passed, page activated");
            email.setMsg("Page " + pagePath + " was activated");

            messageGateway = messageGatewayService.getGateway(Email.class);
            messageGateway.send((Email) email);
        } catch (RepositoryException | LoginException e) {
            LOG.error(EXCEPTION_ERROR + e.getMessage());
        } catch (MailingException | EmailException e) {
            LOG.error(MAIL_ERROR + e.getMessage());
        }
    }
}
