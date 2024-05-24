import { MongoClient } from 'mongodb';

const handler = async (event) => {
  // Conexión a la base de datos MongoDB
  const uri = "mongodb://ec2-3-90-191-160.compute-1.amazonaws.com:27017";
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
            city: user.location, // Supongo que la ubicación se almacena en "location"
            id: user.id,
          },
          claimsToSuppress: ["email"], // Suprimir el reclamo de correo electrónico si es necesario
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
