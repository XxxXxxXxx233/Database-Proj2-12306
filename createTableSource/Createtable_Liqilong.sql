create table users (user_id serial primary key,
                    user_name varchar(45) not null,
                    id_card_num varchar(45) not null,
                    phone_number varchar(45) not null,
                    unique (id_card_num));

create table orders(order_id serial primary key ,
                   user_id int not null ,
                   ticket_id int not null ,
                   create_date date not null ,
                   order_status varchar(3) not null ,
                   depart_city int not null ,
                   arrive_city int not null ,
                   foreign key(user_id) references users(user_id)
                   );

create table ticket_type(ticket_type_id serial primary key ,
                    train_id int not null ,
                    dates date not null ,
                    seat_type varchar not null ,
                    depart_station int not null ,
                    arrive_station int not null ,
                    rest_num int ,
                    price double precision ,
                    unique (train_id,dates,seat_type,depart_station,arrive_station),
                    foreign key (train_id) references train(train_id),
                    foreign key (depart_station) references station(station_id),
                    foreign key (arrive_station) references station(station_id)
                    );

create table ticket(ticket_id serial primary key ,
                    ticket_type int not null ,
                    carriage_position int not null ,
                    seat_position varchar(3) not null ,
                    available char not null ,
                    foreign key (ticket_type) references ticket_type(ticket_type_id)
                    );

create table order_user_ticket(order_id int ,
                               ticket_id int ,
                               user_id int ,
                               status varchar(3) not null ,
                               primary key (order_id, ticket_id, user_id) ,
                               unique (order_id, ticket_id, user_id) ,
                                foreign key (order_id) references orders(order_id),
                                foreign key (ticket_id) references ticket(ticket_id),
                                foreign key (user_id) references users(user_id)
                               );
