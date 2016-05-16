var checkValidity = function(user){


    if (user.description == undefined)
        user.description = '';

    if (user.allergy == undefined)
        user.allergy = '';

    if (user.religion == undefined)
        user.religion = '';

    return (user);
};

