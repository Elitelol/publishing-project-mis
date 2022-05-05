import {useParams} from "react-router-dom";
import {
    Button,
    Container,
    Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography
} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";
import {useContext, useEffect, useState} from "react";
import Task from "../models/Task";
import TaskRow from "../components/TaskRow";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {UserContext, UserProvider} from "../auth";


const TaskPage = () => {
    const {id} = useParams()
    const [context, setContext] = useContext(UserContext);

    const [showNotCompleted, setShowNotCompleted] = useState<boolean>(true);
    const [showInProgress, setShowInProgress] = useState<boolean>(false);
    const [showCompleted, setShowCompleted] = useState<boolean>(false);
    const [showUserTasks, setShowUserTasks] = useState<boolean>(false);
    const [notCompletedTasks, setNotCompletedTask] = useState<Task[]>([]);
    const [inProgressTasks, setInProgressTasks] = useState<Task[]>([]);
    const [completedTasks, setCompletedTasks] = useState<Task[]>([]);
    const [userTasks, setUserTasks] = useState<Task[]>([]);

    useEffect(() => {
        axios.get<Task[]>(ApiUrl() + "task/" + id + "/notStartedTasks").then(response => setNotCompletedTask(response.data))
        axios.get<Task[]>(ApiUrl() + "task/" + id + "/inProgressTasks").then(response => setInProgressTasks(response.data))
        axios.get<Task[]>(ApiUrl() + "task/" + id + "/completedTasks").then(response => setCompletedTasks(response.data))
        axios.get<Task[]>(ApiUrl() + "task/userTasks/" + id + "/" + context.data?.id).then(response => setUserTasks(response.data))
    })

    const handleNotCompleted = () => {
        setShowNotCompleted(true);
        setShowInProgress(false);
        setShowCompleted(false);
        setShowUserTasks(false);
    }

    const handleInProgress = () => {
        setShowNotCompleted(false);
        setShowInProgress(true);
        setShowCompleted(false);
        setShowUserTasks(false);
    }

    const handleCompleted = () => {
        setShowNotCompleted(false);
        setShowInProgress(false);
        setShowCompleted(true);
        setShowUserTasks(false);
    }

    const handleUsers = () => {
        setShowNotCompleted(false);
        setShowInProgress(false);
        setShowCompleted(false);
        setShowUserTasks(true);
    }

    return(
        <Container>
            <NavigationGroup id = {id}/>
            <Typography variant = "h2">Publication tasks</Typography>
            <Button onClick={handleNotCompleted}>Not started tasks</Button>
            <Button onClick={handleInProgress}>In progress tasks</Button>
            <Button onClick={handleCompleted}>Completed tasks</Button>
            <Button onClick={handleUsers}>My tasks</Button>
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
                            showNotCompleted && notCompletedTasks.map(task => {
                                return <TaskRow value = {task}/>
                            })
                        }
                        {
                            showInProgress && inProgressTasks.map(task => {
                                return <TaskRow value = {task}/>
                            })
                        }
                        {
                            showCompleted && completedTasks.map(task => {
                                return <TaskRow value = {task}/>
                            })
                        }
                        {
                            showUserTasks && userTasks.map(task => {
                                return <TaskRow value = {task}/>
                            })
                        }
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    )
}

export default TaskPage