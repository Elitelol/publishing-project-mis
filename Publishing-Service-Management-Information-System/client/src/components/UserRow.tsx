import {Button, TableCell, TableRow} from "@mui/material";
import User from "../models/User";
import {useNavigate} from "react-router-dom";

const UserRow = ({value} : User | any) => {

    const navigate = useNavigate();

    const handleNavigate = () => {
        navigate("/" + value.role + "/" + value.id);
    }

    return(
        <TableRow key={value.id} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell align="right">{value.id}</TableCell>
            <TableCell align="right">{value.username}</TableCell>
            <TableCell align="right">{value.firstName}</TableCell>
            <TableCell align="right">{value.lastName}</TableCell>
            <TableCell align="right">{value.role}</TableCell>
            <TableCell align="right">
                <Button variant = "text" onClick ={handleNavigate}>
                    View
                </Button>
            </TableCell>
        </TableRow>
    )
}

export default UserRow