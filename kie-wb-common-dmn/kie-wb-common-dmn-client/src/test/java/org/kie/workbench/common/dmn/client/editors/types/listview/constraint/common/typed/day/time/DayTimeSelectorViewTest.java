/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.dmn.client.editors.types.listview.constraint.common.typed.day.time;

import java.util.Optional;
import java.util.function.Consumer;

import com.google.gwt.event.dom.client.BlurEvent;
import elemental2.dom.Element.OnchangeCallbackFn;
import elemental2.dom.Event;
import elemental2.dom.HTMLInputElement;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.dmn.client.editors.types.listview.constraint.common.typed.common.MinMaxValueHelper;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.kie.workbench.common.dmn.client.editors.types.listview.constraint.common.typed.day.time.DayTimeValue.NONE;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MinMaxValueHelper.class})
public class DayTimeSelectorViewTest {

    @Mock
    private HTMLInputElement daysInput;

    @Mock
    private HTMLInputElement hoursInput;

    @Mock
    private HTMLInputElement minutesInput;

    @Mock
    private HTMLInputElement secondsInput;

    @Mock
    private DayTimeSelector presenter;

    @Mock
    private Consumer<Event> consumerEvent;

    @Mock
    private Consumer<BlurEvent> consumerBlurEvent;

    private DayTimeSelectorView view;

    @Before
    public void setup() {
        view = spy(new DayTimeSelectorView(daysInput, hoursInput, minutesInput, secondsInput));
        view.init(presenter);
    }

    @Test
    public void testSetupEventHandlers() {

        final OnchangeCallbackFn onChangeHandler = mock(OnchangeCallbackFn.class);
        mockStatic(MinMaxValueHelper.class);
        doReturn(onChangeHandler).when(view).getOnChangeHandler();
        daysInput.onchange = null;
        hoursInput.onchange = null;
        minutesInput.onchange = null;
        secondsInput.onchange = null;

        view.setupEventHandlers();

        assertEquals(daysInput.onchange, onChangeHandler);
        assertEquals(hoursInput.onchange, onChangeHandler);
        assertEquals(minutesInput.onchange, onChangeHandler);
        assertEquals(secondsInput.onchange, onChangeHandler);

        verifyStatic(MinMaxValueHelper.class);
        MinMaxValueHelper.setupMinMaxHandlers(daysInput);

        verifyStatic(MinMaxValueHelper.class);
        MinMaxValueHelper.setupMinMaxHandlers(hoursInput);

        verifyStatic(MinMaxValueHelper.class);
        MinMaxValueHelper.setupMinMaxHandlers(minutesInput);

        verifyStatic(MinMaxValueHelper.class);
        MinMaxValueHelper.setupMinMaxHandlers(secondsInput);
    }

    @Test
    public void testGetValue() {

        daysInput.value = "2";
        hoursInput.value = "4";
        minutesInput.value = "8";
        secondsInput.value = "16";

        final DayTimeValue value = view.getValue();

        assertEquals(value.getDays(), new Integer(2));
        assertEquals(value.getHours(), new Integer(4));
        assertEquals(value.getMinutes(), new Integer(8));
        assertEquals(value.getSeconds(), new Integer(16));
    }

    @Test
    public void testGetValueWhenValuesAreBlank() {

        daysInput.value = "";
        hoursInput.value = "";
        minutesInput.value = "";
        secondsInput.value = "";

        final DayTimeValue value = view.getValue();

        assertEquals(value.getDays(), NONE);
        assertEquals(value.getHours(), NONE);
        assertEquals(value.getMinutes(), NONE);
        assertEquals(value.getSeconds(), NONE);
    }

    @Test
    public void testSetValue() {

        daysInput.value = "something";
        hoursInput.value = "something";
        minutesInput.value = "something";
        secondsInput.value = "something";

        view.setValue(new DayTimeValue(2, 4, 8, 16));

        assertEquals(daysInput.value, "2");
        assertEquals(hoursInput.value, "4");
        assertEquals(minutesInput.value, "8");
        assertEquals(secondsInput.value, "16");
    }

    @Test
    public void testSetValueWithZeroValues() {

        daysInput.value = "something";
        hoursInput.value = "something";
        minutesInput.value = "something";
        secondsInput.value = "something";

        view.setValue(new DayTimeValue());

        assertEquals(daysInput.value, "");
        assertEquals(hoursInput.value, "");
        assertEquals(minutesInput.value, "");
        assertEquals(secondsInput.value, "");
    }

    @Test
    public void testSetOnValueChanged() {

        final Consumer<Event> expected = (e) -> { /* Nothing. */ };

        view.setOnValueChanged(expected);

        final Optional<Consumer<Event>> actual = view.getOnValueChangedConsumer();

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void testSetOnValueInputBlur() {

        final Consumer<BlurEvent> expected = (e) -> { /* Nothing. */ };

        view.setOnValueInputBlur(expected);

        final Optional<Consumer<BlurEvent>> actual = view.getOnValueInputBlurConsumer();

        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    public void testOnDaysInputBlurEvent() {
        final BlurEvent blurEvent = mock(BlurEvent.class);
        doNothing().when(view).onBlurHandler(any());
        view.onDaysInputBlurEvent(blurEvent);
        verify(view).onBlurHandler(blurEvent);
    }

    @Test
    public void testOnHoursInputBlurEvent() {
        final BlurEvent blurEvent = mock(BlurEvent.class);
        doNothing().when(view).onBlurHandler(any());
        view.onHoursInputBlurEvent(blurEvent);
        verify(view).onBlurHandler(blurEvent);
    }

    @Test
    public void testOnMinutesInputBlurEvent() {
        final BlurEvent blurEvent = mock(BlurEvent.class);
        doNothing().when(view).onBlurHandler(any());
        view.onMinutesInputBlurEvent(blurEvent);
        verify(view).onBlurHandler(blurEvent);
    }

    @Test
    public void testOnSecondsInputBlurEvent() {
        final BlurEvent blurEvent = mock(BlurEvent.class);
        doNothing().when(view).onBlurHandler(any());
        view.onSecondsInputBlurEvent(blurEvent);
        verify(view).onBlurHandler(blurEvent);
    }

    @Test
    public void testSelect() {
        view.select();
        verify(daysInput).select();
    }

    @Test
    public void testGetOnChangeHandler() {

        final Event event = mock(Event.class);
        final Optional<Consumer<Event>> optionalConsumer = Optional.of(consumerEvent);

        doReturn(optionalConsumer).when(view).getOnValueChangedConsumer();

        view.getOnChangeHandler().onInvoke(event);

        verify(consumerEvent).accept(event);
    }

    @Test
    public void testOnBlurHandlerWhenTargetIsNotDayTimeInput() {

        final BlurEvent blurEvent = mock(BlurEvent.class);
        final Object target = mock(Object.class);
        final Optional<Consumer<BlurEvent>> optionalConsumer = Optional.of(consumerBlurEvent);

        doReturn(optionalConsumer).when(view).getOnValueInputBlurConsumer();
        doReturn(target).when(view).getEventTarget(blurEvent);

        view.onBlurHandler(blurEvent);

        verify(consumerBlurEvent).accept(blurEvent);
    }

    @Test
    public void testOnBlurHandlerWhenTargetIsDayTimeInput() {

        final BlurEvent blurEvent = mock(BlurEvent.class);
        final Optional<Consumer<BlurEvent>> optionalConsumer = Optional.of(consumerBlurEvent);

        doReturn(optionalConsumer).when(view).getOnValueInputBlurConsumer();
        doReturn(daysInput).when(view).getEventTarget(blurEvent);

        view.onBlurHandler(blurEvent);

        verify(consumerBlurEvent, never()).accept(blurEvent);
    }
}
