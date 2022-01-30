import React, {useState} from "react";
import {Pressable, Text, TextInput, View} from "react-native";
import {styles} from "./CreateGroupFormStyles";
import {globalStyles} from "../../../styles/globalStyles";
import DropDownPicker from 'react-native-dropdown-picker';
import {doPost} from "../../../utils/fetchUtils";
import {showErrorToast, showSuccessToast} from "../../../utils/toasts";

export const CreateGroupForm = ({showModal, addGroup}) => {

    const [pickerOpened, setPickerOpened] = useState(false);
    const [pickerActionPerformed, setPickerActionPerformed] = useState(false);
    const [groupName, setGroupName] = useState('');
    const [currency, setCurrency] = useState(null);

    const isDisabled = groupName.length < 1 || currency === null;

    const [currencies, setCurrencies] = useState([
        {label: 'PLN', value: 'PLN'},
        {label: 'EUR', value: 'EUR'},
        {label: 'USD', value: 'USD'}
    ]);

    const validateCurrency = () => {
        if (currency === null) {
            showErrorToast("Nie wybrano waluty.\nWybierz jeden element z listy.")
        }
    }

    const validateGroupName = () => {
        if (groupName.length < 1) {
            showErrorToast("Niepoprawna nazwa grupy.\nMinimalna długość to 1 znak.")
        }
    }

    const onCreate = () => {
        const body = {
            name: groupName,
            currency: currency
        }

        doPost("/api/groups", body)
            .then(response => response.json())
            .then(json => addGroup(json))
            .then(() => showSuccessToast(`Grupa ${groupName} została pomyślnie utworzona.`))
            .catch(error => console.log(error))
        showModal(false)
    }


    if (pickerActionPerformed) {
        validateCurrency();
        setPickerActionPerformed(false);
    }

    return (
        <View style={styles.container}>
            <View style={styles.inputContainer}>
                <Text style={styles.label}>Nazwa grupy</Text>
                <TextInput
                    style={[globalStyles.inputAndroid, styles.input]}
                    placeholder={"Wprowadź nazwę grupy..."}
                    placeholderTextColor={'grey'}
                    value={groupName}
                    onChangeText={setGroupName}
                    onEndEditing={() => validateGroupName()}
                />
                <Text style={styles.label}>Waluta</Text>
                <DropDownPicker
                    style={styles.picker}
                    textStyle={styles.pickerText}
                    placeholder={"Wybierz walutę.."}
                    placeholderStyle={{color: 'grey'}}
                    theme={"DARK"}
                    onSelectItem={setCurrency}
                    onClose={() => setPickerActionPerformed(true)}
                    open={pickerOpened}
                    value={currency}
                    items={currencies}
                    setOpen={setPickerOpened}
                    setValue={setCurrency}
                    setItems={setCurrencies}
                />
            </View>
            <View style={styles.buttonsContainer}>
                <Pressable
                    style={globalStyles.iconButton}
                    onPress={() => showModal(false)}
                >
                    <Text style={styles.buttonText}>
                        ANULUJ
                    </Text>
                </Pressable>
                <Pressable
                    style={globalStyles.iconButton}
                    disabled={isDisabled}
                    onPress={() => onCreate()}
                >
                    <Text style={[styles.buttonText,
                        isDisabled ? styles.buttonTextLocked : undefined]}
                    >
                        UTWÓRZ
                    </Text>
                </Pressable>
            </View>
        </View>
    )
}