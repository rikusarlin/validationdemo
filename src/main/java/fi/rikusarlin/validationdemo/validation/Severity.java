package fi.rikusarlin.validationdemo.validation;

import javax.validation.Payload;

public final class Severity {
    public final class Info implements Payload {
    }

    public final class Error implements Payload {
    }
}