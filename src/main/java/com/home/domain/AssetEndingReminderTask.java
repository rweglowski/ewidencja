package com.home.domain;

import com.home.repository.AssetRepository;
import com.home.repository.EmployeeRepository;
import com.home.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AssetEndingReminderTask {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MailService mailService;

    private Clock clock;

    @Autowired
    AssetEndingReminderTask(Clock clock){this.clock = clock;}


    @Scheduled(cron = "0 0 8 * * ?")
    public void sendAssetEndReminders(){
        List<AssetEndingSoonReminder> reminders = prepareReminders();
        for(AssetEndingSoonReminder reminder : reminders){
            mailService.sendAssetEndingSoonReminder(reminder);
        }

    }

    private List<AssetEndingSoonReminder> prepareReminders(){
        List<AssetEndingSoonReminder> preparedReminders = new ArrayList<>();
        List<Asset> endingAssets7days = assetRepository.findByEndDateOfUse(LocalDate.now(clock).plusDays(7));
        List<Asset> endingAssets1day = assetRepository.findByEndDateOfUse(LocalDate.now(clock).plusDays(1));
        List<Asset> allEndingAssets = new ArrayList<>();
        allEndingAssets.addAll(endingAssets1day);
        allEndingAssets.addAll(endingAssets7days);
        for(Asset asset : allEndingAssets){
            for(Employee employee : asset.getEmployees()){
                preparedReminders.add(AssetEndingSoonReminder.builder()
                    .employeeId(employee.getId())
                    .firstName(employee.getFirstName())
                    .surname(employee.getSurname())
                    .email(employee.getEmail())
                    .assetName(asset.getName())
                    .assetInventoryCode(asset.getInventoryCode())
                    .build());
            }
        }
        return preparedReminders;
    }
}
