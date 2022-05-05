import Task from "../models/Task";
import {Button, TableCell, TableRow} from "@mui/material";
import {useNavigate} from "react-router-dom";

const TaskRow = ({value}: Task | any) => {

    const navigate = useNavigate();

    const handleNavigate = () => {

    }

    return(
        <TableRow key={value.taskId} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell component="th" scope="row">
                {value.taskId}
            </TableCell>
            <TableCell align="right">{value.taskName}</TableCell>
            <TableCell align="right">{value.taskType}</TableCell>
            <TableCell align="right">{value.startDate}</TableCell>
            <TableCell align="right">{value.dueDate}</TableCell>
            <TableCell align="right">{value.progress}</TableCell>
            <TableCell align="right">
                <Button variant = "text" onClick ={handleNavigate}>
                    View
                </Button>
            </TableCell>
        </TableRow>
    )
}

export default TaskRow