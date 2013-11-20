package com.github.feedly.service;

import com.foxykeep.datadroid.service.RequestService;
import com.github.feedly.operations.FeedlyOperation;
import com.github.feedly.util.RequestFactory;

public class FeedlyService extends RequestService {
	
	@Override
    public Operation getOperationForType(int requestType) {
            switch (requestType) {
            	case RequestFactory.REQUEST_CATEGORIES:
                    System.out.println("I was in service");
            		return new FeedlyOperation();
            	default:
                    return null;
            }
    }

}
