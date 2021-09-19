// To parse this data:
//
//   import { Convert, HttpErrorResponseConvert } from "./util";
//
//   const error = Convert.toError(json);
//
// These functions will throw an error if the JSON doesn't
// match the expected interface, even if the JSON is valid.

import { HttpErrorResponse } from "@angular/common/http";

export interface HttpErrorResponseConvert {
    errors: {
        timestamp: Date;
        details: string;
    }
} 

// Converts JSON strings to/from your types
// and asserts the results of JSON.parse at runtime
export class Convert {

    public static toError(json: HttpErrorResponse): string {

        const result: HttpErrorResponseConvert = JSON.parse(json.error);  

        return result.errors.details;
    }

}