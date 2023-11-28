package be.bds.bdsbes.utils;

public @interface ValidationErrorUtil {
    String AssertFalse = "validation.constraints.AssertFalse";
    String AssertTrue = "validation.constraints.AssertTrue";
    String DecimalMax = "validation.constraints.DecimalMax";
    String DecimalMin = "validation.constraints.DecimalMin";
    String Digits = "validation.constraints.Digits";
    String Email = "validation.constraints.Email";
    String Future = "validation.constraints.Future";
    String FutureOrPresent = "validation.constraints.FutureOrPresent";
    String Max = "validation.constraints.Max";
    String Min = "validation.constraints.Min";
    String Negative = "validation.constraints.Negative";
    String NegativeOrZero = "validation.constraints.NegativeOrZero";
    String NotBlank = "validation.constraints.NotBlank";
    String NotEmpty = "validation.constraints.NotEmpty";
    String NotNull = "validation.constraints.NotNull";
    String Null = "validation.constraints.Null";
    String Past = "validation.constraints.Past";
    String PastOrPresent = "validation.constraints.PastOrPresent";
    String Pattern = "validation.constraints.Pattern";
    String Positive = "validation.constraints.Positive";
    String PositiveOrZero = "validation.constraints.PositiveOrZero";
    String Size = "validation.constraints.Size";
    String Duplicate = "validation.constraints.Duplicate";
    String NotFound = "validation.constraints.NotFound";
    String Invalid = "validation.constraints.Invalid";
    String InUse = "validation.constraints.InUse";
    String CheckIn = "validation.constraints.CheckIn";
    String CheckDateBook = "validation.constraints.CheckDateBook";
    String CheckInBeforeDateNow = "validation.constraints.CheckInBeforeDateNow";

    String DeleteRoomOrder = "validation.constraints.DeleteRoomOrder";
}