package io.axoniq.demo.giftcard.reporting;

import io.axoniq.demo.giftcard.api.IssuedEvent;
import io.axoniq.demo.giftcard.api.ReportedEvent;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

public class MyReportingInterceptor implements MessageHandlerInterceptor<EventMessage<?>> {

    private final EventGateway eventGateway;

    public MyReportingInterceptor(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public Object handle(UnitOfWork<? extends EventMessage<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        Object proceed = interceptorChain.proceed();
        EventMessage<?> event = unitOfWork.getMessage();
        if (event.getPayloadType() == IssuedEvent.class) {
            eventGateway.publish(new ReportedEvent("New card issued"));
        }
        return proceed;
    }
}
