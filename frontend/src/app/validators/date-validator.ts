import { AbstractControl, ValidatorFn } from '@angular/forms';

export enum ValidateDate {
    BEFORE,
    TODAY_OR_AFTER,
    AFTER,
}

// validation function
export function validateDate(compareTo: Date, direction: ValidateDate): ValidatorFn {
    return (c: AbstractControl) => {
        let isValid: boolean;
        console.log('Validator compareTo: ' + compareTo);
        console.log('Validator enteredDate: ' + c.value);
        if (compareTo === undefined) {
            return null;
        } else if (direction === ValidateDate.BEFORE) {
            isValid = c.value < compareTo;
        } else if (direction === ValidateDate.TODAY_OR_AFTER) {
            isValid = c.value >= compareTo;
        } else {
            isValid = c.value > compareTo;
        }
        console.log('Valid: ' + isValid);
        if (isValid) {
            return null;
        } else {
            return {
                invalidDate: {
                    valid: false
                }
            };
        }
    };
}


