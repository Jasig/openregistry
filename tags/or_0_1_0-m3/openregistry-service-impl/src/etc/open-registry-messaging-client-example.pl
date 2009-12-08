#
# Copyright (C) 2009 Jasig, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
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