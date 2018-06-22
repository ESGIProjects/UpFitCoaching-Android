package com.mycoaching.mycoaching.Util.Miscellaneous;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by kevin on 14/06/2018.
 */

public class EventDecorator implements DayViewDecorator {

    private final int[] colors;
    private final HashSet<CalendarDay> dates;


    public EventDecorator(Collection<CalendarDay> dates, int... colors) {
        this.dates = new HashSet<>(dates);
        this.colors = colors;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        if(colors.length == 1){
            view.addSpan(new DotSpan(9,colors[0]));
        }
        view.addSpan((new CustomMultipleDotSpan(9, colors)));
    }
}