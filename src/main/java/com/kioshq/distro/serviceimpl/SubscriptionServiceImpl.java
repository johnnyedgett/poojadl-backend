package com.kioshq.distro.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kioshq.distro.dto.SubscriptionTemplate;
import com.kioshq.distro.entity.DistributionList;
import com.kioshq.distro.entity.Subscription;
import com.kioshq.distro.entity.Person;
import com.kioshq.distro.repository.SubscriptionRepository;
import com.kioshq.distro.service.AuthenticationService;
import com.kioshq.distro.service.DistributionListService;
import com.kioshq.distro.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	private static final Logger LOG = LoggerFactory.getLogger(SubscriptionService.class);

	@Autowired
	DistributionListService dlService;

	@Autowired
	AuthenticationService authService;

	@Autowired
	SubscriptionRepository subscriptionRepository;

	@Override
	public Subscription createNewSubscription(SubscriptionTemplate subscription) {
		// check if the dl exists
		// make sure the subscription is valid (ie not already subscribed)
		DistributionList distributionList = dlService.findDistributionList(subscription.getListId());
		Person user = authService.findUser(subscription.getUserId());

		/* Validate the user and distribution list exist */
		if (user == null || distributionList == null)
			// TODO do some exception
			LOG.error("PANIC");

		/* Validate this user -> dl subscription does NOT already exist */
		boolean subscriptionExists = user.getSubscriptions().stream()
				.anyMatch(sub -> sub.getDistributionList().getId() == distributionList.getId());

		if (subscriptionExists)
			// TODO do some exception
			LOG.error("PANIC");

//		public Subscription(User user, DistributionList distributionList, SubscriptionType subscriptionType) {
		Subscription newSubscription = new Subscription(user, distributionList, subscription.getSubscriptionType());
		return subscriptionRepository.save(newSubscription);
	}

}
