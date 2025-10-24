package com.sive.bookingsystem.service.event.message;

import com.sive.bookingsystem.dto.appointment.AppointmentDTO;

public class Data {
    AppointmentDTO appointment;
    //there can be more objects here
    public Data(AppointmentDTO appointment) {
        this.appointment = appointment;
    }

    public Data() {
    }

    public AppointmentDTO getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentDTO appointment) {
        this.appointment = appointment;
    }
}
