import Publication from "../models/Publication";
import {useNavigate} from "react-router-dom";
import {
    Button,
    Paper,
    Table, TableBody, TableCell,
    TableContainer,
    TableHead, TableRow,
    Typography
} from "@mui/material";
import PublicationRow from "./PublicationRow";
import {UserContext} from "../auth";
import {useContext} from "react";

const Publications = ({publications}: any) => {
    const navigate = useNavigate();
    const [context, setContext] = useContext(UserContext);

    return (
        <>
            <Typography variant = "h2" >My publication works</Typography>
            {
                context.data?.role === "Author" ? <Button onClick = {
                    () => navigate("/publication/new")}>Add new publication</Button> : ""
            }
            <TableContainer component = {Paper}>
                <Table sx = {{minWidth: 650}}>
                    <TableHead>
                        <TableRow>
                            <TableCell align ="right">Publication Id</TableCell>
                            <TableCell align ="right">Publication name</TableCell>
                            <TableCell align ="right">Publication type</TableCell>
                            <TableCell align ="right">Genre</TableCell>
                            <TableCell align ="right">Author</TableCell>
                            <TableCell align ="right">Progress status</TableCell>
                            <TableCell align ="right">Progress</TableCell>
                            <TableCell align ="right">Manager</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {publications.map((value: Publication) => (
                            <PublicationRow key={value.publicationId} value = {value}/>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    )
}

export default Publications