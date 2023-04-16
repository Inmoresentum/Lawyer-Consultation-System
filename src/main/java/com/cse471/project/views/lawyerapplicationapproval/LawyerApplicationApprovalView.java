package com.cse471.project.views.lawyerapplicationapproval;

import com.cse471.project.entity.LawyerRoleApplication;
import com.cse471.project.service.Lawyer.LawyerRoleApplicationService;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("List Of Applications")
@Route(value = "list-of-lawyer-application", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class LawyerApplicationApprovalView extends VerticalLayout {

    private final Div mainContainerDiv = new Div();
    private final LawyerRoleApplicationService lawyerRoleApplicationService;

    public LawyerApplicationApprovalView(LawyerRoleApplicationService lawyerRoleApplicationService) {
        this.lawyerRoleApplicationService = lawyerRoleApplicationService;
        addClassName("lawyer-app-approval-view-main-page");
        mainContainerDiv.addClassName("lawyer-app-approval-view-main-container");
        var listOfAllApplication = lawyerRoleApplicationService.findAllLawyerApplication();
        for (var application : listOfAllApplication) {
            mainContainerDiv.add(createAnApplicationCard(application));
        }
    }

    private Div createAnApplicationCard(LawyerRoleApplication application) {
        Div applicationCard = new Div();

        return applicationCard;
    }
}
