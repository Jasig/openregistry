use Net::Stomp;

# subscribe to messages from the topic 'openregistry.identifier-change.out'. Replace 'hostname' with the URI of your messaging broker instance
my $messaging_broker = Net::Stomp->new( { hostname => 'localhost', port => '61613' } );
$messaging_broker->connect( { login => 'hello', passcode => 'there' } );
$messaging_broker->subscribe(
    {   destination =>'/topic/openregistry.identifier-change.out',
        'ack'=>'client',
        'activemq.prefetchSize' => 20
    }
);

#Listen for incoming messages on the subscribed topic and process them (just send 'em to STDOUT in this case)
while (1) {
    my $frame = $messaging_broker->receive_frame;
    print $frame->body."\n"; # do something here
    $messaging_broker->ack( { frame => $frame } );
}
$messaging_broker->disconnect;