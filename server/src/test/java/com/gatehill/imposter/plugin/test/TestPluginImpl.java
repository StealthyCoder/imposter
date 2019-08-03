package com.gatehill.imposter.plugin.test;

import com.gatehill.imposter.ImposterConfig;
import com.gatehill.imposter.plugin.config.ConfiguredPlugin;
import com.gatehill.imposter.util.HttpUtil;
import io.vertx.ext.web.Router;

import javax.inject.Inject;
import java.util.List;

import static com.gatehill.imposter.util.AsyncUtil.handleRoute;

/**
 * @author Pete Cornish {@literal <outofcoffee@gmail.com>}
 */
public class TestPluginImpl extends ConfiguredPlugin<TestPluginConfig> {
    @Inject
    private ImposterConfig imposterConfig;

    private List<TestPluginConfig> configs;

    @Override
    protected Class<TestPluginConfig> getConfigClass() {
        return TestPluginConfig.class;
    }

    @Override
    protected void configurePlugin(List<TestPluginConfig> configs) {
        this.configs = configs;
    }

    @Override
    public void configureRoutes(Router router) {
        router.get("/example").handler(handleRoute(imposterConfig, vertx, routingContext ->
                routingContext.response().setStatusCode(HttpUtil.HTTP_OK).end()));
    }

    public List<TestPluginConfig> getConfigs() {
        return configs;
    }
}
