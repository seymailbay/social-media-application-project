import React, { useState } from "react";
import {
    makeStyles,
    Card,
    CardActions,
    CardContent,
    CardMedia,
    Button,
    Typography,
    Modal,
    ListItem,
    List,
    ListItemSecondaryAction,
    Radio,
} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        maxWidth: 400,
        margin: theme.spacing(5),
    },
    modal: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
    },
}));

function Avatar(props) {
    const {avatarId,userId,userName} =props;
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(avatarId);

    const saveAvatar = () => {
        fetch("http://localhost:8083/users/" + localStorage.getItem("currentUser"), {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": localStorage.getItem("tokenKey"),
            },
            body: JSON.stringify({
                avatar: selectedValue,
            }),
        })
            .then((res) => res.json())
            .catch((err) => console.log(err))
    }


    const handleChange = (event) => {
        setSelectedValue(parseInt(event.target.value));
    };

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        saveAvatar();
    };

    return (
        <div>
            <Card className={classes.root}>
                <CardMedia
                    component="img"
                    alt="User Avatar"
                    height="400"
                    image={`/avatar/avatar${selectedValue}.png`}
                    title="User Avatar"
                />
                <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                       about you
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                        {userName}
                    </Typography>
                </CardContent>
                <CardActions>
                    {localStorage.getItem("currentUser") === userId ? <Button size="small" color="primary"  onClick={handleOpen}>
                        Change cat
                    </Button> : ""}
                </CardActions>
            </Card>
            <Modal
                className={classes.modal}
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <List dense>
                    {[0, 1, 2, 3, 4, 5, 6].map((key) => {
                        const labelId = `checkbox-list-secondary-label-${key}`;
                        return (
                            <ListItem key={key} button>
                                <CardMedia
                                    style={{ maxWidth: 80 }}
                                    component="img"
                                    alt={`Avatar n°${key}`}
                                    image={`/avatar/avatar${key}.png`}
                                    title="User Avatar"
                                />
                                <ListItemSecondaryAction>
                                    <Radio
                                        edge="end"
                                        value={key}
                                        onChange={handleChange}
                                        checked={selectedValue === key}
                                        inputProps={{ "aria-labelledby": labelId }}
                                    />
                                </ListItemSecondaryAction>
                            </ListItem>
                        );
                    })}
                </List>
            </Modal>
        </div>
    );
}

export default Avatar;