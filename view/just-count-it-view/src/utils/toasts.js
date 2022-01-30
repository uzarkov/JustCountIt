import React from 'react';
import Toast, { BaseToast, ErrorToast } from 'react-native-toast-message';

const toastsCommonConfig = {
    successContainerStyle: {
        backgroundColor: '#03DAC5',
    },
    errorContainerStyle: {
        backgroundColor: '#CF6679',
    },
    text: {
        fontSize: 20,
        fontWeight: 'bold',
        color: 'black'
    },
    text2NumberOfLines: 3
}

export const toastsConfig = {
    success: (props) => (
        <BaseToast
            {...props}
            style={{ borderLeftColor: '#03DAC5', borderColor: '#03DAC5'}}
            contentContainerStyle={toastsCommonConfig.successContainerStyle}
            text2Style={toastsCommonConfig.text}
            text2NumberOfLines={toastsCommonConfig.text2NumberOfLines}
        />
    ),
    error: (props) => (
        <ErrorToast
            {...props}
            style={{ borderLeftColor: '#CF6679', borderColor: '#CF6679' }}
            contentContainerStyle={toastsCommonConfig.errorContainerStyle}
            text2Style={toastsCommonConfig.text}
            text2NumberOfLines={toastsCommonConfig.text2NumberOfLines}
        />
    ),
};

export const showErrorToast = (text) => {
    Toast.show({
        type: 'error',
        position: 'bottom',
        text2: text
    })
}

export const showSuccessToast = (text) => {
    Toast.show({
        type: 'success',
        position: 'bottom',
        text2: text
    })
}