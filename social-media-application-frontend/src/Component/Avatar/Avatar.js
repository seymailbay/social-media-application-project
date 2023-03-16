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
    const {avatarId} =props;
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(avatarId);

    const handleChange = (event) => {
        setSelectedValue(parseInt(event.target.value));
    };

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
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
                        Name
                    </Typography>
                    <Typography variant="body2" color="textSecondary" component="p">
                        About you
                    </Typography>
                </CardContent>
                <CardActions>
                    <Button type="button" onClick={handleOpen}>
                        What Kind Of Cat Are You?
                    </Button>
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