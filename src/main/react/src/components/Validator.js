import React, {Component} from 'react';
import {observable} from "mobx";

class Validator extends Component {
    @observable ERROR = null;
    MOBILE_DELETE_WARNING = "info.sletteMobilVarsel"
    MOBILE_ERROR = "error.mobileError"
    MOBILE_REPEAT_ERROR = "error.mobileRepeatError"
    EMAIL_ERROR = "error.emailError"
    EMAIL_REPEAT_ERROR = "error.emailRepeatError"
    ALL_CONTACT_INFO_REMOVED = "error.allContactInfoIsRemoved"

    validateMobile(current){
        if(current.mobile.length){
            // check for number starting with 4 or 9, optional landcode (0047,+47) and min 8 digits, max 20
            if (!current.mobile.replace(/\s+/g, '').match("^([0]{2}[0-9]{2})?([+][0-9]{2})?[49][0-9]{7,19}$")){
                return this.MOBILE_ERROR;
            }
        }

        if (current.mobileConfirmed && current.mobile !== current.mobileConfirmed){
            return this.MOBILE_REPEAT_ERROR;
        }

        return this.preventUserFromDeletingAllContactInfo(current)
    }

    validateEmail(current){
        if (current.email.length){
            // check correct structure mail@domain, does not allow "(),:;<>@[\] and space
            if (!(current.email.match("^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])$"))) {
                return this.EMAIL_ERROR;
            }
        }

        if (current.emailConfirmed && current.email !== current.emailConfirmed){
            return this.EMAIL_REPEAT_ERROR;
        }

        return this.preventUserFromDeletingAllContactInfo(current)
    }

    /* custom for idk */
    isMobileRemoved(current){
        if(current.history.mobile.length > 0 && current.mobile.length === 0){
            return this.MOBILE_DELETE_WARNING;
        }
    }



    preventUserFromDeletingAllContactInfo(current){

        if(current.history.email.length > 0 || current.history.mobile.length > 0) {
            if(current.email.length === 0 && current.mobile.length === 0){
                return this.ALL_CONTACT_INFO_REMOVED;
            }
        }
    }
}

export default new Validator();