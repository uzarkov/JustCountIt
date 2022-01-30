import { useEffect, useState } from "react"
import { View, Text, ScrollView } from "react-native"
import { ExpenditureItem } from "./ExpenditureItem"
import { styles } from "./ExpendituresViewStyles"
import ActionButton from "react-native-action-button"
import { CreateExpenditureView } from "./create/CreateExpenditureView"
import { doGet } from "../../../utils/fetchUtils"

export const ExpendituresView = ({ user, groupMetadata, setShowNavigation }) => {
    const [inCreation, setInCreation] = useState(false)
    const [expenditures, setExpenditures] = useState([])

    const totalCost = sumOfExpendituresPrices(expenditures)
    const myTotalCost = sumOfExpendituresPrices(expenditures.filter(e => e.userId === user.id))

    const fetchExpenditures = () => {
        doGet(`/api/expenditures/${groupMetadata.id}`)
            .then(response => response.json())
            .then(json => setExpenditures(json))
            .catch(err => console.log(err.message))
    }

    useEffect(() => {
        fetchExpenditures()
    }, [])

    if (inCreation) {
        return <CreateExpenditureView
            user={user}
            groupMetadata={groupMetadata}
            onCancel={() => {
                setShowNavigation(true)
                setInCreation(false)
            }}
            onAdd={newExpenditure => setExpenditures([...expenditures, newExpenditure])}
        />
    }

    return (
        <View style={styles.container}>
            <View style={{ flex: 0.73 }}>
                <ScrollView>
                    {sorted(expenditures).map(e => (
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
                    onPress={() => {
                        setShowNavigation(false)
                        setInCreation(true)
                    }}
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

const sorted = (expenditures) => {
    const result = [...expenditures]
    result.sort((a, b) => a.date < b.date ? 1 : (a.date > b.date ? -1 : 0))
    return result
}