import React, {Component} from 'react';

import {withTranslation,Trans} from "react-i18next";
import kontaktinfoStore from "../stores/KontaktinfoStore";
import ContentInfoBox from "../common/ContentInfoBox";
import ContentHeader from "../common/ContentHeader";
import DigdirButtons from "../common/DigdirButtons";
import DigdirButton from "../common/DigdirButton";
import DigdirForm from "../common/DigdirForm";
import {inject} from "mobx-react";
import autobind from "autobind-decorator";

@inject("kontaktinfoStore")
class Error extends Component {

    @autobind
    handleSubmit(e) {
        e.preventDefault();
        this.props.kontaktinfoStore.updateGotoUrl().then(() => {
            document.getElementById('postForm').submit();
        })
    }

    render () {
        const {kontaktinfoStore} = this.props;
        const current = kontaktinfoStore.current;

        return (
            <div>
                <ContentHeader title="title" sub_title="page_title.error"/>
                <ContentInfoBox content="info.errorpage" state="warning" />

                <DigdirForm id="postForm" method="post" action={this.props.kontaktinfoStore.gotoUrl} onSubmit={this.handleSubmit}>

                    <DigdirButtons>
                        <DigdirButton
                            tabIndex="4"
                            id="idporten.inputbutton.CONTINUE_CONFIRM"
                            name="idporten.inputbutton.CONTINUE_CONFIRM"
                            form="postForm"
                            type="submit"
                            textKey="button.confirm"
                        />

                    </DigdirButtons>
                </DigdirForm>
            </div>
        );
    }
}

const compose = (...rest) => x => rest.reduceRight((y, f) => f(y), x);
export default compose(withTranslation())(Error);