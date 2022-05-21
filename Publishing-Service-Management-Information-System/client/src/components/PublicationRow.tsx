import {Box, Button, LinearProgress, LinearProgressProps, TableCell, TableRow, Typography} from "@mui/material";
import {useNavigate} from "react-router-dom";
import Publication from "../models/Publication";

const PublicationRow = ({value}: Publication | any) => {

    const navigate = useNavigate();

    const handleNavigate = () => {
        navigate("/publication/" + value.publicationId);
    }

    function LinearProgressWithLabel(props: LinearProgressProps & { value: number }) {
        return (
            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                <Box sx={{ width: '100%', mr: 1 }}>
                    <LinearProgress variant="determinate" {...props} />
                </Box>
                <Box sx={{ minWidth: 35 }}>
                    <Typography variant="body2" color="text.secondary">{`${Math.round(
                        props.value,
                    )}%`}</Typography>
                </Box>
            </Box>
        );
    }

    return<>
        <TableRow key={value.publicationId} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
            <TableCell component="th" scope="row">
                {value.publicationId}
            </TableCell>
            <TableCell align="right">{value.name}</TableCell>
            <TableCell align="right">{value.publicationType}</TableCell>
            <TableCell align="right">{value.genre}</TableCell>
            <TableCell align="right">{value.author.firstName + " " + value.author.lastName}</TableCell>
            <TableCell align="right">{value.progressStatus}</TableCell>
            <TableCell align="right">
                <LinearProgressWithLabel value={value.progressPercent}/>
            </TableCell>
            <TableCell align="right">{value.manager ? value.manager.firstName + " " + value.manager.lastName : ""}</TableCell>
            <TableCell align="right">
                <Button variant = "text" onClick ={handleNavigate}>
                    View
                </Button>
            </TableCell>
        </TableRow>
    </>
}

export default PublicationRow