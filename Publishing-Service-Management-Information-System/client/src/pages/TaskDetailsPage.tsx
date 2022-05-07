import React, {useContext, useEffect, useState} from "react";
import Task from "../models/Task";
import {
    Button,
    Card,
    CardContent,
    Container,
    FormControl,
    InputLabel, List, ListItem, ListItemButton, ListItemText,
    MenuItem,
    Select, SelectChangeEvent,
    TextField,
    Typography
} from "@mui/material";
import {useParams} from "react-router-dom";
import axios, {AxiosResponse} from "axios";
import ApiUrl from "../config/api.config";
import Progress from "../models/Progress";
import TaskType from "../models/TaskType";
import NavigationGroup from "../components/NavigationGroup";
import Comments from "../components/Comments";
import User from "../models/User";
import {UserContext} from "../auth";
import UserComment from "../models/UserComment";
import Navbar from "../components/Navbar";
import SideMenu from "../components/SideMenu";
import {DatePicker } from "@mui/x-date-pickers";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import Role from "../models/Role";

const TaskDetailsPage = () => {

    const {id} = useParams();
    const [context, setContext] = useContext(UserContext);

    useEffect(() => {
        axios.get<User>(ApiUrl() + "user/" + context.data?.id).then(response => setCommentator(response.data))
        axios.get<Task>(ApiUrl() + "task/" + id).then(response => handleStateChange(response))
        axios.get<Progress[]>(ApiUrl() + "type/taskProgress").then(response => setProgressSelection(response.data))
        axios.get<TaskType[]>(ApiUrl() + "type/tasks").then(response => setTypeSelection(response.data))
    }, [])

    const [task, setTask] = useState<Task>({
        comments: [],
        description: "",
        dueDate: null,
        progress: "Not Started",
        publicationId: null,
        responsiblePeople: [],
        startDate: null,
        taskId: null,
        taskName: "",
        taskType: "Editing"
    })

    const [comments, setComments] = useState(task.comments)
    const [description, setDescription] = useState(task.description)
    const [dueDate, setDueDate] = useState<Date | null>(task.dueDate)
    const [progress, setProgress] = useState(task.progress)
    const [publicationId, setPublicationId] = useState(task.publicationId)
    const [responsiblePeople, setResponsiblePeople] = useState<User[]>(task.responsiblePeople)
    const [startDate, setStartDate] = useState<Date | null>(task.startDate)
    const [taskId, setTaskId] = useState(task.taskId)
    const [taskName, setTaskName] = useState(task.taskName)
    const [taskType, setTaskType] = useState(task.taskType)

    const [typeSelection, setTypeSelection] = useState<TaskType[]>([])
    const [progressSelection, setProgressSelection] = useState<Progress[]>([]);
    const [selectedProgress, setSelectedProgress] = useState<string>(progress);
    const [selectedType, setSelectedType] = useState<string>(taskType);
    const [taskComments, setTaskComments] = useState<UserComment[]>([]);
    const [addUserText, setAddUserText] = useState<string>("");
    const [commentator, setCommentator] = useState<User>({
        email: "", firstName: "", id: null, lastName: "", role: "", username: ""
    })
    const [newResponsibleUser, setNewResponsibleUser] = useState<User>({
        email: "",
        firstName: "",
        id: null,
        lastName: "",
        role: "",
        username: ""
    })
    const [userId, setUserId] = useState<number | null>(null);
    const [username, setUsername] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [role, setRole] = useState<string>("");

    const handleStateChange = (response: AxiosResponse<any, any>) => {
        setTask(response.data)
        setComments(response.data.comments)
        setDescription(response.data.description)
        setDueDate(response.data.dueDate)
        setProgress(response.data.progress)
        setPublicationId(response.data.publicationId)
        setResponsiblePeople(response.data.responsiblePeople)
        setStartDate(response.data.startDate)
        setTaskId(response.data.taskId)
        setTaskName(response.data.taskName)
        setTaskType(response.data.taskType)
        setTaskComments(response.data.comments)
    }

    const handleProgressChange = (event: SelectChangeEvent) => {
        setSelectedProgress(event.target.value as string);
    }

    const handleTypeChange = (event: SelectChangeEvent) => {
        setSelectedType(event.target.value as string);
    }

    const handleSave = () => {
        axios.post<Task>(ApiUrl() + "task", {
            taskId,
            taskName,
            taskType: selectedType,
            startDate,
            responsiblePeople,
            publicationId,
            progress: selectedProgress,
            dueDate,
            description,
            comments
        }).then(response => handleStateChange(response))
    }

    const handleAddUser = () => {
        axios.get<User>(ApiUrl() + "user/byUsername/" + addUserText).then(response => {
            setUserId(response.data.id)
            setUsername(response.data.username)
            setEmail(response.data.email)
            setFirstName(response.data.firstName)
            setLastName(response.data.lastName)
            setRole(response.data.role)
        })
        responsiblePeople.push({
            id: userId,
            username,
            email,
            firstName,
            lastName,
            role
        })
        axios.post<Task>(ApiUrl() + "task", {
            taskId,
            taskName,
            taskType: selectedType,
            startDate,
            responsiblePeople,
            publicationId,
            progress: selectedProgress,
            dueDate,
            description,
            comments
        }).then(response => handleStateChange(response))
        setUserId(null)
        setUsername("")
        setEmail("")
        setFirstName("")
        setLastName("")
        setRole("")
        setAddUserText("");
    }

    const handleRemoveUser = () => {

    }

    return(
        <>
            <Navbar/>
            <SideMenu/>
            <Container>
                <NavigationGroup id = {task.publicationId} />
                <Card>
                    <Typography variant = "h2">Task details</Typography>
                    <CardContent>
                        <TextField fullWidth label="Task name" margin = "normal" value={taskName} onChange = {event => setTaskName(event.target.value)}/>
                        <TextField fullWidth label="Task description" margin = "normal" value={description} onChange = {event => setDescription(event.target.value)}/>
                        <Typography variant = "h4">Responsible people</Typography>
                        <List>
                            {
                                responsiblePeople.map(worker => {
                                    return (
                                        <ListItem>
                                            <ListItemText primary={
                                                <Typography>
                                                    {worker.username + " (" + worker.firstName + " " + worker.lastName + ")"}
                                                </Typography>
                                            }
                                                secondary={<ListItemButton onClick ={handleRemoveUser}>
                                                    Remove
                                                </ListItemButton>}
                                            />
                                        </ListItem>
                                    )
                                })
                            }
                        </List>
                        <TextField fullWidth label = "Add responsible user by username" margin = "normal" value={addUserText} onChange={event => setAddUserText(event.target.value)} />
                        {
                            addUserText.length > 0 && <Button onClick = {handleAddUser}>Add</Button>
                        }
                        <FormControl fullWidth margin = "normal">
                            <InputLabel>Task type</InputLabel>
                            <Select onChange = {handleTypeChange} value = {selectedType}>
                                {
                                    typeSelection.map(t => {
                                        return <MenuItem value = {t.type}> {t.type} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                            <DatePicker
                                label="Start date"
                                value={startDate}
                                onChange={(newValue) => {
                                    setStartDate(newValue);
                                }}
                                renderInput={(params) => <TextField {...params} />}
                             />
                        </LocalizationProvider>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                            <DatePicker
                                label="Due date"
                                value={dueDate}
                                onChange={(newValue) => {
                                    setDueDate(newValue);
                                }}
                                renderInput={(params) => <TextField {...params} />}
                            />
                        </LocalizationProvider>
                        <FormControl fullWidth margin = "normal">
                            <InputLabel>Progress status</InputLabel>
                            <Select onChange = {handleProgressChange} value = {selectedProgress}>
                                {
                                    progressSelection.map(t => {
                                        return <MenuItem value = {t.status}> {t.status} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <Button onClick = {handleSave}>Save</Button>
                    </CardContent>
                </Card>
                <Comments comments={taskComments} url ={"task"} entityId={task.taskId} setComments={setTaskComments} commentator={commentator}/>
            </Container>
        </>
    )
}

export default TaskDetailsPage