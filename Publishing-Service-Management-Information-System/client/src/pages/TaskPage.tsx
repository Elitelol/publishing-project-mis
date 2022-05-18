import {useNavigate, useParams} from "react-router-dom";
import {
    Box,
    Button,
    Container,
    FormControl,
    InputLabel,
    MenuItem,
    Modal,
    Paper,
    Select,
    SelectChangeEvent,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@mui/material";
import NavigationGroup from "../components/NavigationGroup";
import React, {useContext, useEffect, useState} from "react";
import Task from "../models/Task";
import TaskRow from "../components/TaskRow";
import axios from "axios";
import ApiUrl from "../config/api.config";
import {UserContext, UserProvider} from "../auth";
import TaskType from "../models/TaskType";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";

const style = {
    position: 'absolute' as 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

const TaskPage = () => {
    const {id} = useParams()
    const [context, setContext] = useContext(UserContext);
    const navigate = useNavigate();

    const [open, setOpen] = useState<boolean>(false);
    const [showNotCompleted, setShowNotCompleted] = useState<boolean>(true);
    const [showInProgress, setShowInProgress] = useState<boolean>(false);
    const [showCompleted, setShowCompleted] = useState<boolean>(false);
    const [showUserTasks, setShowUserTasks] = useState<boolean>(false);
    const [notCompletedTasks, setNotCompletedTask] = useState<Task[]>([]);
    const [inProgressTasks, setInProgressTasks] = useState<Task[]>([]);
    const [completedTasks, setCompletedTasks] = useState<Task[]>([]);
    const [userTasks, setUserTasks] = useState<Task[]>([]);
    const [taskName, setTaskName] = useState<string>("");
    const [typeSelection, setTypeSelection] = useState<TaskType[]>([])
    const [selectedType, setSelectedType] = useState<string>("Editing");
    const [description, setDescription] = useState("");
    const [startDate, setStartDate] = useState(null);
    const [dueDate, setDueDate] = useState(null)

    useEffect(() => {
        axios.get<Task[]>(ApiUrl() + "task/" + id + "/notStartedTasks").then(response => setNotCompletedTask(response.data))
        axios.get<Task[]>(ApiUrl() + "task/" + id + "/inProgressTasks").then(response => setInProgressTasks(response.data))
        axios.get<Task[]>(ApiUrl() + "task/" + id + "/completedTasks").then(response => setCompletedTasks(response.data))
        axios.get<Task[]>(ApiUrl() + "task/userTasks/" + id + "/" + context.data?.id).then(response => setUserTasks(response.data))
        axios.get<TaskType[]>(ApiUrl() + "type/tasks").then(response => setTypeSelection(response.data))
    }, [])

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

    const handleOpen = () => {
        setOpen(true);
    }
    const handleClose = () => {
        setOpen(false);
    }

    const handleSave = () => {
        axios.post<Task>(ApiUrl() + "task", {
            taskId: null,
            taskName,
            taskType: selectedType,
            startDate: "2021-01-01",
            responsiblePeople: [],
            publicationId: id,
            progress: "Not Started",
            dueDate: "2021-01-02",
            description,
            comments: []
        }).then(response => navigate("/task/" + response.data.taskId))
    }

    const handleTypeChange = (event: SelectChangeEvent) => {
        setSelectedType(event.target.value as string);
    }

    return(
        <>
            <Navbar/>
            <SideMenu/>
            <Container>
                <NavigationGroup id = {id} unallowedToClick={false} unallowedAttach={false}/>
                <Typography variant = "h2">Publication tasks</Typography>
                <Button onClick={handleNotCompleted}>Not started tasks</Button>
                <Button onClick={handleInProgress}>In progress tasks</Button>
                <Button onClick={handleCompleted}>Completed tasks</Button>
                {
                    context.data?.role === "Worker" && <Button onClick={handleUsers}>My tasks</Button>
                }
                <Container>
                    {
                        context.data?.role === "Publication Manager" && <Button onClick={handleOpen}>Add task</Button>
                    }
                </Container>
                <Modal open = {open} onClose = {handleClose}>
                    <Box sx = {style}>
                        <Typography variant = "h3">New task</Typography>
                        <TextField fullWidth label="Task name" margin = "normal" value={taskName} onChange = {event => setTaskName(event.target.value)}/>
                        <TextField fullWidth label="Task description" margin = "normal" value={description} onChange = {event => setDescription(event.target.value)}/>
                        <FormControl fullWidth>
                            <InputLabel>Task type</InputLabel>
                            <Select onChange = {handleTypeChange} value = {selectedType}>
                                {
                                    typeSelection.map(t => {
                                        return <MenuItem value = {t.type}> {t.type} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <Button onClick ={handleSave}>Create</Button>
                    </Box>
                </Modal>
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
        </>
    )
}

export default TaskPage