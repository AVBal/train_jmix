package com.company.train.screen.dashboard;

import io.jmix.charts.component.PieChart;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.data.impl.MapDataItem;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@UiController("train_DashboardScreen")
@UiDescriptor("dashboard-screen.xml")
public class DashboardScreen extends Screen {
    @Autowired
    private PieChart performerPieChart;
    @Autowired
    private PieChart customerPieChart;
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInit(InitEvent event) {
        String sumByOrgQueryTemplate = "select orgName, sum(c.totalAmount) from train_Contract c group by orgName";
        selectDataAndFillPieChart(sumByOrgQueryTemplate.replaceAll("orgName", "c.customer.name"),
                customerPieChart);
        selectDataAndFillPieChart(sumByOrgQueryTemplate.replaceAll("orgName", "c.performer.name"),
                performerPieChart);
    }

    private void selectDataAndFillPieChart(String query, PieChart chart) {
        List<KeyValueEntity> result = dataManager.loadValues(query)
                .properties("name", "totalAmount")
                .list();
        for (KeyValueEntity kv : result) {
            chart.addData(MapDataItem.of("organizationName", kv.getValue("name"),
                    "totalAmount", kv.getValue("totalAmount")));
        }
    }
}