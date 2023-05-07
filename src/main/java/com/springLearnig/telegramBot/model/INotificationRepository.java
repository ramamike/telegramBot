package com.springLearnig.telegramBot.model;

import org.springframework.data.repository.CrudRepository;

public interface INotificationRepository extends CrudRepository<Notification, Long> {
}
