package com.gatehill.imposter.plugin;

import com.gatehill.imposter.plugin.config.ResourceConfig;
import com.gatehill.imposter.script.ResponseBehaviour;
import com.gatehill.imposter.script.ResponseBehaviourType;
import com.gatehill.imposter.service.ResponseService;
import com.google.inject.Injector;
import io.vertx.ext.web.RoutingContext;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Pete Cornish {@literal <outofcoffee@gmail.com>}
 */
public interface ScriptedPlugin<C extends ResourceConfig> {
    default void scriptHandler(C config, RoutingContext routingContext,
                               Injector injector, Consumer<ResponseBehaviour> defaultBehaviourHandler) {

        scriptHandler(config, routingContext, injector, null, defaultBehaviourHandler);
    }

    default void scriptHandler(C config, RoutingContext routingContext, Injector injector,
                               Map<String, Object> additionalContext,
                               Consumer<ResponseBehaviour> defaultBehaviourHandler) {

        final ResponseService responseService = injector.getInstance(ResponseService.class);

        try {
            final ResponseBehaviour responseBehaviour = responseService.getResponseBehaviour(
                    routingContext, config, additionalContext, Collections.emptyMap());

            if (ResponseBehaviourType.IMMEDIATE_RESPONSE.equals(responseBehaviour.getBehaviourType())) {
                routingContext.response()
                        .setStatusCode(responseBehaviour.getStatusCode())
                        .end();

            } else {
                // default behaviour
                defaultBehaviourHandler.accept(responseBehaviour);
            }

        } catch (Exception e) {
            routingContext.fail(e);
        }
    }
}
