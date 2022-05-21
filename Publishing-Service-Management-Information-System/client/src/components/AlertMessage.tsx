import {Alert, AlertColor, IconButton} from "@mui/material";
import React from "react";
import CloseIcon from '@mui/icons-material/Close';
import {isArray} from "util";

type Props = {
    severity: AlertColor | undefined
    message: string[];
    setShowMessage: React.Dispatch<React.SetStateAction<boolean>>
}

const AlertMessage = ({severity, message, setShowMessage}: Props) => {
    return (
        <Alert variant = "filled" severity={severity} action={
            <IconButton
                aria-label="close"
                color="inherit"
                size="small"
                onClick={() => {
                    setShowMessage(false);
                }}
            >
                <CloseIcon fontSize="inherit" />
            </IconButton>
        }>
            {
                message.map(value => {
                    return value + " ";
                })
            }
        </Alert>
    )
}

export default AlertMessage