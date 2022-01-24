import moment from "moment"
import { View, Text } from "react-native"
import { styles } from "./ExpenditureItemStyles"

export const ExpenditureItem = ({ title, price, username, date, isSameUser, currencySymbol }) => {
    const today = moment()
    const expenditureDate = moment(date, "YYYY-MM-DD")
    const daysBefore = today.diff(expenditureDate, 'days')

    return (
        <View style={styles.container}>
            <View style={styles.textContainer}>
                <Text style={styles.topText}>{title}</Text>
                <Text style={[styles.topText, { textAlign: 'right' }]}>{price.toFixed(2)}{currencySymbol}</Text>
            </View>
            <View style={styles.textContainer}>
                <Text style={styles.bottomText}>Zapłacone przez: {username}{isSameUser ? ' (Ja)' : ''}</Text>
                <Text style={[styles.bottomText, { textAlign: 'right' }]}>{getExpenditureDateString(daysBefore)}</Text>
            </View>
            <View style={styles.stripe}></View>
        </View>
    )
}

const getExpenditureDateString = (daysBefore) => {
    switch (daysBefore) {
        case 0:
            return "Dzisiaj"
        case 1:
            return "Wczoraj"
        default:
            return `${daysBefore} dni temu`
    }
}