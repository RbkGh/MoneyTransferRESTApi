package com.revolut.module;

import com.google.inject.AbstractModule;
import com.revolut.controllers.AccountController;
import com.revolut.controllers.AccountControllerImpl;
import com.revolut.repository.AccountRepository;
import com.revolut.repository.AccountReposityDefaultImpl;
import com.revolut.util.JsonParser;
import com.revolut.util.JsonParserImpl;

/**
 * author: acerbk
 * Date: 2019-08-25
 * Time: 03:59
 * Google Guice Framework DI Configuration
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(AccountController.class).to(AccountControllerImpl.class);
        bind(AccountRepository.class).to(AccountReposityDefaultImpl.class);
        bind(JsonParser.class).to(JsonParserImpl.class);
    }
}
