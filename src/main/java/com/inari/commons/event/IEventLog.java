package com.inari.commons.event;

/** This can be used as a event logging connector interface by connecting it to
 *  a given IEventDispatcher implementation. The log method gets called on every
 *  notify call, getting the specified event that is notifed.
 */
public interface IEventLog {
    
    /** Logs the specified Event. Is called on notify event methods of a given IEventDispatcher implementation
     * @param event The event that is notified
     */
    void log( Event<?> event );

}
