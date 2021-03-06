import React, {useContext, useEffect, useState} from "react";
import Task from "../models/Task";
import {
    AlertColor,
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
import {useNavigate, useParams} from "react-router-dom";
import axios, {AxiosResponse} from "axios";
import ApiUrl from "../config/api.config";
import Progress from "../models/Progress";
import TaskType from "../models/TaskType";
import NavigationGroup from "../components/NavigationGroup";
import Comments from "../components/Comments";
import User from "../models/User";
import {UserContext} from "../auth";
import UserComment from "../models/UserComment";
import {DatePicker } from "@mui/x-date-pickers";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import NavMenu from "../components/NavMenu";
import AlertMessage from "../components/AlertMessage";

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
    const [disabled, setDisabled] = useState<boolean>(true);
    const[showMessage, setShowMessage] = useState<boolean>(false);
    const [message, setMessage] = useState<string[]>([]);
    const [severity, setSeverity] = useState<AlertColor | undefined>("success");
    const navigate = useNavigate();

    const handleStateChange = (response: AxiosResponse<any, any>) => {
        const newResponsiblePeople = response.data.responsiblePeople;
        setTask(response.data)
        setComments(response.data.comments)
        setDescription(response.data.description)
        setDueDate(response.data.dueDate)
        setProgress(response.data.progress)
        setPublicationId(response.data.publicationId)
        setResponsiblePeople(newResponsiblePeople)
        setStartDate(response.data.startDate)
        setTaskId(response.data.taskId)
        setTaskName(response.data.taskName)
        setTaskType(response.data.taskType)
        setTaskComments(response.data.comments)
        setSelectedProgress(response.data.progress)
        const userIds = newResponsiblePeople.map((user: { id: any; }) => {
            return user.id;
        })
        setDisabled(!(userIds.includes(context.data?.id) || context.data?.role === "Publication Manager"));
    }

    const handleProgressChange = (event: SelectChangeEvent) => {
        setSelectedProgress(event.target.value as string);
    }

    const handleTypeChange = (event: SelectChangeEvent) => {
        setSelectedType(event.target.value as string);
    }

    const handleSave = async () => {
        await axios.post<Task>(ApiUrl() + "task", {
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
        }).then(response => {
            handleStateChange(response)
            setMessage(["Task saved."]);
            setShowMessage(true);
            setSeverity("success");
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        })
    }

    const handleAddUser = async () => {
        await axios.get<User>(ApiUrl() + "user/byUsername/" + addUserText).then(response => {
            setUserId(response.data.id)
            setUsername(response.data.username)
            setEmail(response.data.email)
            setFirstName(response.data.firstName)
            setLastName(response.data.lastName)
            setRole(response.data.role)
            responsiblePeople.push({
                id: response.data.id,
                username: response.data.username,
                email: response.data.email,
                firstName: response.data.firstName,
                lastName: response.data.lastName,
                role: response.data.role
            })
        }).then(() => {
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
            }).then(response => {
                handleStateChange(response)
                setUserId(null)
                setUsername("")
                setEmail("")
                setFirstName("")
                setLastName("")
                setRole("")
                setAddUserText("");
                setMessage(["Worker added."]);
                setShowMessage(true);
                setSeverity("success");
            }).catch(error => {
                setMessage(error.response.data.message)
                setShowMessage(true);
                setSeverity("error");
            })
        }).catch(error => {
            setMessage(error.response.data.message)
            setShowMessage(true);
            setSeverity("error");
        })
    }

    const handleRemoveUser = (username: string) => {
        setResponsiblePeople(responsiblePeople.filter(user => user.username !== username));
    }

    const handleDelete = async () => {
        await axios.delete(ApiUrl() + "task/" + id)
            .then(() => navigate("/" + task.publicationId + "/task")).catch(error => {
                setMessage(error.response.data.message)
                setShowMessage(true);
                setSeverity("error");
            })
    }

    return(
        <>
            <NavMenu/>
            <Container>
                <NavigationGroup id = {task.publicationId} unallowedToClick={false} unallowedAttach={false}/>
                <Card>
                    <Typography variant = "h2">Task details</Typography>
                    {
                        showMessage && <AlertMessage severity={severity} message={message} setShowMessage={setShowMessage}/>
                    }
                    <CardContent>
                        <TextField disabled={disabled} fullWidth label="Task name" margin = "normal" value={taskName} onChange = {event => setTaskName(event.target.value)}/>
                        <TextField disabled={disabled} fullWidth label="Task description" margin = "normal" value={description} onChange = {event => setDescription(event.target.value)}/>
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
                                                secondary={<ListItemButton disabled={context.data?.role !== "Publication Manager"} onClick ={() => handleRemoveUser(worker.username)}>
                                                    Remove
                                                </ListItemButton>}
                                            />
                                        </ListItem>
                                    )
                                })
                            }
                        </List>
                        <TextField disabled={context.data?.role !== "Publication Manager"} fullWidth label = "Add responsible user by username" margin = "normal"
                                   value={addUserText} onChange={event => setAddUserText(event.target.value)} />
                        {
                            addUserText.length > 0 && <Button onClick = {handleAddUser}>Add</Button>
                        }
                        <FormControl fullWidth margin = "normal">
                            <InputLabel>Task type</InputLabel>
                            <Select disabled={context.data?.role !== "Publication Manager"} onChange = {handleTypeChange} value = {selectedType}>
                                {
                                    typeSelection.map(t => {
                                        return <MenuItem value = {t.type}> {t.type} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                            <DatePicker
                                disabled={context.data?.role !== "Publication Manager"}
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
                                disabled={context.data?.role !== "Publication Manager"}
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
                            <Select disabled={disabled} onChange = {handleProgressChange} value = {selectedProgress}>
                                {
                                    progressSelection.map(t => {
                                        return <MenuItem value = {t.status}> {t.status} </MenuItem>
                                    })
                                }
                            </Select>
                        </FormControl>
                        {
                            !disabled && <Button onClick = {handleSave} variant="contained" color="success">Save</Button>
                        }
                        {
                            context.data?.role === "Publication Manager" && task.progress !== "Completed"
                            && <Button onClick = {handleDelete} variant="contained" color="error">Delete</Button>
                        }
                    </CardContent>
                </Card>
                <Comments comments={taskComments} url ={"task"} entityId={task.taskId} setComments={setTaskComments} commentator={commentator}/>
            </Container>
        </>
    )
}

export default TaskDetailsPage