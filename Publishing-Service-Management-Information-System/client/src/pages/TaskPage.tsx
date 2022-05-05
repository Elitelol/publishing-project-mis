import {useParams} from "react-router-dom";
import {
    Button,
    Container,
    Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography
} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";
import {useEffect, useState} from "react";
import Task from "../models/Task";
import TaskRow from "../components/TaskRow";


const TaskPage = () => {
    const {id} = useParams()

    useEffect(() => {
        axios.g
    })

    const [showNotCompleted, setShowNotCompleted] = useState<boolean>(true);
    const [showInProgress, setShowInProgress] = useState<boolean>(false);
    const [showCompleted, setShowCompleted] = useState<boolean>(false);
    const [notCompletedTasks, setNotCompletedTask] = useState<Task[]>([]);
    const [inProgressTasks, setInProgressTasks] = useState<Task[]>([]);
    const [completedTasks, setCompletedTasks] = useState<Task[]>([]);

    const handleNotCompleted = () => {
        setShowNotCompleted(true);
        setShowInProgress(false);
        setShowCompleted(false);
    }

    const handleInProgress = () => {
        setShowNotCompleted(false);
        setShowInProgress(true);
        setShowCompleted(false);
    }

    const handleCompleted = () => {
        setShowNotCompleted(false);
        setShowInProgress(false);
        setShowCompleted(true);
    }

    return(
        <Container>
            <NavigationGroup id = {id}/>
            <Typography variant = "h2">Publication tasks</Typography>
            <Button onClick={handleNotCompleted}>Not started tasks</Button>
            <Button onClick={handleInProgress}>In progress tasks</Button>
            <Button onClick={handleCompleted}>Completed tasks</Button>
            <Container>
                <Button>Add task</Button>
            </Container>
            <TableContainer component = {Paper}>
                <Table sx = {{minWidth: 650}}>
                    <TableHead>
                        <TableRow>
                            <TableCell align ="right">Task Id</TableCell>
                            <TableCell align ="right">Task name</TableCell>
                            <TableCell align ="right">Task type</TableCell>
                            <TableCell align ="right">Task start date</TableCell>
                            <TableCell align ="right">Task due date</TableCell>
                            <TableCell align ="right">Task progress</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            showNotCompleted && <TaskRow/>
                        }
                        {

                        }
                        {

                        }
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    )
}

export default TaskPage