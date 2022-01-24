import { OutlinedTextField } from "rn-material-ui-textfield"

export const InputField = ({ label, otherProps, inputRef }) => {
    return (
        <OutlinedTextField
            tintColor={'#BB86FC'}
            textColor={'whitesmoke'}
            baseColor={'#BB86FC'}
            lineWidth={0}
            label={label}
            ref={inputRef}
            {...otherProps}
        />
    )
}