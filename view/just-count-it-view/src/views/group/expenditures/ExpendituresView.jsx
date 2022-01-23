import { useState } from "react"
import { View, Text, ScrollView } from "react-native"
import { ExpenditureItem } from "./ExpenditureItem"
import { styles } from "./ExpendituresViewStyles"
import ActionButton from "react-native-action-button"

export const ExpendituresView = ({ user, groupMetadata }) => {
    const [expenditures, setExpenditures] = useState(sampleExpenditures)

    const totalCost = sumOfExpendituresPrices(expenditures)
    const myTotalCost = sumOfExpendituresPrices(expenditures.filter(e => e.userId === user.id))

    return (
        <View style={styles.container}>
            <View style={{ flex: 0.73 }}>
                <ScrollView>
                    {expenditures.map(e => (
                        <ExpenditureItem
                            key={e.id}
                            title={e.title}
                            price={e.price}
                            date={e.date}
                            username={e.username}
                            isSameUser={e.userId === user.id}
                            currencySymbol={groupMetadata.currency.symbol}
                        />
                    ))}
                </ScrollView>
            </View>
            <View style={{ flex: 0.1 }}>
                <ActionButton
                    buttonColor={"#BB86FC"}
                    buttonTextStyle={{ color: '#000' }}
                    position={"center"}
                    zIndex={1}
                />
                <View style={styles.footerContainer}>
                    <View style={styles.textContainer}>
                        <Text style={[styles.topText, { textAlign: 'left' }]}>Mój całkowity koszt</Text>
                        <Text style={[styles.topText, { textAlign: 'right' }]}>Całkowity koszt</Text>
                    </View>
                    <View style={styles.textContainer}>
                        <Text style={[styles.bottomText, { textAlign: 'left' }]}>{myTotalCost}{groupMetadata.currency.symbol}</Text>
                        <Text style={[styles.bottomText, { textAlign: 'right' }]}>{totalCost}{groupMetadata.currency.symbol}</Text>
                    </View>
                </View>
            </View>
        </View>
    )
}

const sumOfExpendituresPrices = (expenditures) => {
    return expenditures.reduce((a, b) => {
        return { price: a.price + b.price }
    }, { price: 0 }).price
}

const sampleExpenditures = [
    {
        id: 1,
        price: 160.23,
        title: "Paliwo",
        date: "2022-01-23",
        username: "Andrzej",
        userId: 101,
    },
    {
        id: 2,
        price: 300,
        title: "Hotel",
        date: "2022-01-22",
        username: "Julia",
        userId: 102,
    },
    {
        id: 3,
        price: 46,
        title: "Kino",
        date: "2022-01-21",
        username: "Robert",
        userId: 103,
    },
]