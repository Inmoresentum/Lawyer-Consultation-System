package com.cse471.project.views.dashboard;


import com.cse471.project.service.UserService.UserService;
import com.cse471.project.views.MainLayout;
import com.cse471.project.views.dashboard.AnalyticService.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import javax.annotation.security.RolesAllowed;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainLayout.class)
@RouteAlias(value = "admin-dashboard", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class DashboardView extends Main {

    public DashboardView(UserService userService) {
        addClassName("dashboard-view");

        Board board = new Board();

        board.addRow(createHighlight("Current users", userService.getTotalNumberOfUsers().toString(), 33.7),
                createHighlight("View events", "54.6k", 112.45),
                createHighlight("Conversion rate", "18%", 3.9),
                createHighlight("Custom metric", "-123.45", 0.0));
        board.addRow(createViewEvents());
        board.addRow(createServiceHealth(),
                createResponseTimes());
        add(board);
    }

    private Component createHighlight(String title, String value,
                                      Double percentage) {
        VaadinIcon icon = VaadinIcon.ARROW_UP;
        String prefix = "";
        String theme = "badge";

        if (percentage == 0) {
            prefix = "±";
        } else if (percentage > 0) {
            prefix = "+";
            theme += " success";
        } else if (percentage < 0) {
            icon = VaadinIcon.ARROW_DOWN;
            theme += " error";
        }

        H2 h2 = new H2(title);
        h2.addClassNames(FontWeight.NORMAL, Margin.NONE,
                TextColor.SECONDARY, FontSize.XSMALL);

        Span span = new Span(value);
        span.addClassNames(FontWeight.SEMIBOLD, FontSize.XXXLARGE);

        Icon i = icon.create();
        i.addClassNames(BoxSizing.BORDER, Padding.XSMALL);

        Span badge = new Span(i, new Span(prefix + percentage));
        badge.getElement().getThemeList().add(theme);

        VerticalLayout layout = new VerticalLayout(h2, span, badge);
        layout.addClassName(Padding.LARGE);
        layout.setPadding(false);
        layout.setSpacing(false);
        return layout;
    }

    private Component createViewEvents() {
        // Header
        Select year = new Select();
        year.setItems("2011", "2012", "2013", "2014", "2015", "2016",
                "2017", "2018", "2019", "2020", "2021", "2022", "2023");
        year.setValue("2022");
        year.setWidth("100px");

        HorizontalLayout header = createHeader("Number Of Viewers", "Month");
        header.add(year);

        // Chart
        Chart chart = new Chart(ChartType.AREASPLINE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("Viewers");

        PlotOptionsAreaspline plotOptions = new PlotOptionsAreaspline();
        plotOptions.setPointPlacement(PointPlacement.ON);
        plotOptions.setMarker(new Marker(false));
        conf.addPlotOptions(plotOptions);

        // Todo: Later change the values that we will fetch from the database
        conf.addSeries(new ListSeries("Dhaka", 189, 191, 291, 396, 501,
                403, 609, 712, 729, 942, 1044, 1247));
        conf.addSeries(new ListSeries("Sylhet", 138, 246, 248, 348, 352,
                353, 463, 573, 778, 779, 885, 887));
        conf.addSeries(new ListSeries("Rajshahi", 65, 65, 166, 171, 293,
                302, 308, 317, 427, 429, 535, 636));
        conf.addSeries(new ListSeries("Outside of Bangladesh", 0, 11, 17, 123, 130, 142,
                248, 349, 452, 454, 458, 462));
        conf.getyAxis().setTitle("Places/Viewers");
        // Add it all together
        VerticalLayout viewEvents = new VerticalLayout(header, chart);
        viewEvents.addClassName(Padding.LARGE);
        viewEvents.setPadding(false);
        viewEvents.setSpacing(false);
        viewEvents.getElement().getThemeList().add("spacing-l");
        return viewEvents;
        // Add more charts
    }

    private Component createServiceHealth() {
        // Header
        HorizontalLayout header = createHeader("Critical Violence", "Number of Incidents");

        // Grid
        Grid<AnalyticService> grid = new Grid<>();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        grid.addColumn(new ComponentRenderer<>(analyticService -> {
            Span status = new Span();
            String statusText = getStatusDisplayName(analyticService);
            status.getElement().setAttribute("aria-label", "Status: " + statusText);
            status.getElement().setAttribute("title", "Status: " + statusText);
            status.getElement().getThemeList().add(getStatusTheme(analyticService));
            return status;
        })).setHeader("").setFlexGrow(0).setAutoWidth(true);
        grid.addColumn(AnalyticService::getCity).setHeader("City").setFlexGrow(1);
        grid.addColumn(AnalyticService::getInput).setHeader("Reported Cases").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(AnalyticService::getOutput).setHeader("Critical Cases").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);

        grid.setItems(new AnalyticService(Status.FAILING, "Dhaka", 324, 1540),
                new AnalyticService(Status.OK, "Comilla", 50, 12),
                new AnalyticService(Status.EXCELLENT, "Sylhet", 10, 2));

        // Add it all together
        VerticalLayout container = new VerticalLayout(header, grid);
        container.addClassName(Padding.LARGE);
        container.setPadding(false);
        container.setSpacing(false);
        container.getElement().getThemeList().add("spacing-l");
        return container;
    }

    private Component createResponseTimes() {
        HorizontalLayout header = createHeader("Response times", "Average across all systems");

        // Chart
        Chart chart = new Chart(ChartType.PIE);
        Configuration conf = chart.getConfiguration();
        conf.getChart().setStyledMode(true);
        chart.setThemeName("gradient");

        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem("System 1", 12.5));
        series.add(new DataSeriesItem("System 2", 12.5));
        series.add(new DataSeriesItem("System 3", 12.5));
        series.add(new DataSeriesItem("System 4", 12.5));
        series.add(new DataSeriesItem("System 5", 12.5));
        series.add(new DataSeriesItem("System 6", 12.5));
        conf.addSeries(series);

        // Add it all together
        VerticalLayout serviceHealth = new VerticalLayout(header, chart);
        serviceHealth.addClassName(Padding.LARGE);
        serviceHealth.setPadding(false);
        serviceHealth.setSpacing(false);
        serviceHealth.getElement().getThemeList().add("spacing-l");
        return serviceHealth;
    }

    private HorizontalLayout createHeader(String title, String subtitle) {
        H2 h2 = new H2(title);
        h2.addClassNames(FontSize.XLARGE, Margin.NONE);

        Span span = new Span(subtitle);
        span.addClassNames(TextColor.SECONDARY, FontSize.XSMALL);

        VerticalLayout column = new VerticalLayout(h2, span);
        column.setPadding(false);
        column.setSpacing(false);

        HorizontalLayout header = new HorizontalLayout(column);
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setSpacing(false);
        header.setWidthFull();
        return header;
    }

    private String getStatusDisplayName(AnalyticService analyticService) {
        Status status = analyticService.getStatus();
        if (status == Status.OK) {
            return "Ok";
        } else if (status == Status.FAILING) {
            return "Failing";
        } else if (status == Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(AnalyticService analyticService) {
        Status status = analyticService.getStatus();
        String theme = "badge primary small";
        if (status == Status.EXCELLENT) {
            theme += " success";
        } else if (status == Status.FAILING) {
            theme += " error";
        }
        return theme;
    }

}
