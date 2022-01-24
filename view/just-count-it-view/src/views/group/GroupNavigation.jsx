import React from 'react'
import { Pressable, Text, View } from "react-native"
import { styles } from "./GroupNavigationStyles"
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";

export const ROUTES = {
    BALANCE: "Bilans",
    EXPENDITURES: "Wydatki",
    GROUP_INFO: "Grupa",
}

export const GroupNavigation = ({ onClicks, currentView }) => {
    return (
        <View style={styles.container}>
            <Button
                icon={<MaterialCommunityIcons name={"bank"} />}
                text={ROUTES.BALANCE}
                isActive={currentView === ROUTES.BALANCE}
                onClick={() => onClicks[`${ROUTES.BALANCE}`]()}
            />
            <Button
                icon={<MaterialIcons name={"account-balance-wallet"} />}
                text={ROUTES.EXPENDITURES}
                isActive={currentView === ROUTES.EXPENDITURES}
                onClick={() => onClicks[`${ROUTES.EXPENDITURES}`]()}
            />
            <Button
                icon={<MaterialIcons name={"people"} />}
                text={ROUTES.GROUP_INFO}
                isActive={currentView === ROUTES.GROUP_INFO}
                onClick={() => onClicks[`${ROUTES.GROUP_INFO}`]()}
            />
        </View>
    )
}

const Button = ({ icon, text, isActive, onClick }) => {
    const commonIconProps = {
        color: isActive ? '#BB86FC' : 'whitesmoke',
        size: 26,
        style: styles.icon
    }

    const textColor = isActive ? '#BB86FC' : 'whitesmoke'
    const stripeColor = isActive ? '#BB86FC' : '#222222'

    return (
        <Pressable style={styles.button} onPress={() => onClick()}>
            {React.cloneElement(icon, { ...commonIconProps })}
            <Text style={[styles.buttonText, { color: textColor }]}>{text}</Text>
            <View style={[styles.activeStripe, { backgroundColor: stripeColor }]}></View>
        </Pressable>
    )
}