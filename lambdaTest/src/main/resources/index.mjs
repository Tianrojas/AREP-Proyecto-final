import { MongoClient } from 'mongodb';

const handler = async (event) => {
    // Conexión a la base de datos MongoDB
    const uri = "mongodb://54.158.42.42:27017";
    const client = new MongoClient(uri);

    try {
        await client.connect();
        const database = client.db('arep_finalproject');
        const collection = database.collection('users');

        // Obtener el usuario del evento basado en el correo electrónico
        const userEmail = event.request.userAttributes.email;
        const user = await collection.findOne({ email: userEmail });

        if (user) {
            // Agregar las reclamaciones específicas del usuario al objeto de respuesta
            event.response = {
                ...event.response,
                claimsOverrideDetails: {
                    claimsToAddOrOverride: {
                        ...event.response.claimsOverrideDetails?.claimsToAddOrOverride,
                        role: user.role,
                        city: user.location,
                        address: user.address,
                        id: user._id,
                    },
                    claimsToSuppress: ["email"],
                },
            };
        } else {
            console.log(`Usuario no encontrado para el correo electrónico: ${userEmail}`);
        }
    } finally {
        await client.close();
    }

    return event;
};

export { handler };
