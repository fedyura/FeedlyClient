package com.github.feedly.service;

import com.foxykeep.datadroid.service.RequestService;
import com.github.feedly.operations.AuthentificationOperation;
import com.github.feedly.operations.GetBestArticlesOperation;
import com.github.feedly.operations.GetCategoryOperation;
import com.github.feedly.util.RequestFactory;

public class FeedlyService extends RequestService {
	
	@Override
    public Operation getOperationForType(int requestType) {
            switch (requestType) {
            	case RequestFactory.REQUEST_CATEGORIES:
                    return new GetCategoryOperation();
            	case RequestFactory.REQUEST_AUTHENTIFICATION:
            		return new AuthentificationOperation();
            	case RequestFactory.REQUEST_BESTARTICLES:
            		return new GetBestArticlesOperation();
            	default:
                    return null;
            }
    }
}
