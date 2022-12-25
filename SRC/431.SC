;**
;**	Logics for room 431 -- Larry On Stage
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 23, 1989
;**


(script#				431)

(public
	rm431				0
)

(include "430.sh")


(enum												;**	RoomScript state values
	intro
	timpRoll4Larry
	somethingsDone
	ohMyGawd
	missCherri
	wheresCherri2
	bringOutLarry
	egoInPlace
	curtainClosed
	nowWhat
	booHiss
	getOff
	initVeggies
	UBdying
	deadOfEmbarrassment
	startDancing
	cueTheBand
	endOfRunway
	throwMoney
	throwMoney2
	throwAll
	danceYourHeartOut
	fade2black
	changeRooms
)


(enum												;**	VeggieScript state values
	VeggieInit
	VeggieStart
	VeggieDone
)


(enum												;**	MoneyScript state values
	MoneyInit
	MoneyStart
	MoneyDone
)


(local
	[aMoney  6]
	[aVeggie 6]

	spotlightX = [ 999
						113
						127
						141
						155
						169
						183
						197
						999	]
	[string  40] 
	[string2 22]
)

(enum		431									;**	egoState values
	UNDER_LIGHTS
	WAITING_TO_DANCE
)


(instance rm431 of Room
	(properties
		picture		430
		horizon		1
	)


	(method (init &tmp i)
		(Load SOUND sTimpaniRoll)
		(Load SOUND sBoosWhistlesCatcalls)
		(Load SOUND sLarryDancing)

		(Load SCRIPT JUMP)
		(Load PICTURE blackPicture)
		(Load FONT BIG_FAT_18)

		(super init)

		(self
			setScript 		RoomScript
		)

		(aCurtain	ignoreActors, init)

		(aActor1 ignoreActors, init)
		(aActor2 ignoreActors, init)
		(aActor3 ignoreActors, init)
		(aActor4 ignoreActors, init)
		(aActor5 ignoreActors, init)

		(= oldGameSpeed (theGame setSpeed 6))

		(= [aMoney  1] 	aActor1)
		(= [aMoney  2] 	aActor2)
		(= [aMoney  3] 	aActor3)
		(= [aMoney  4] 	aActor4)
		(= [aMoney  5] 	aActor5)

		(= [aVeggie 1] 	aActor1)		;**	Talk about recycling!
		(= [aVeggie 2] 	aActor2)
		(= [aVeggie 3] 	aActor3)
		(= [aVeggie 4] 	aActor4)
		(= [aVeggie 5] 	aActor5)

		(NormalEgo)
		(ego
			view				vLroom,
			setLoop			black,
			setCycle			Walk,
			setStep			2 1,
			setCycle			Walk,
			setPri			1,
			posn 				16 75,
			ignoreActors,
			init,
		)
		(HandsOff)
		(RoomScript changeState timpRoll4Larry)
	)
)


(instance RoomScript of Script
	(method (doit &tmp i)
		(super doit)


		(if (and (== SND_DONE (music prevSignal))
					(== (music number) sLarryDancing))
			(self changeState fade2black)
		)

		(if (== egoState UNDER_LIGHTS)
			(cond						;**	These are the runway controls in order:
				((& (ego onControl origin) cBlue)		(= i 1))
				((& (ego onControl origin) cGreen)		(= i 2))
				((& (ego onControl origin) cCyan)		(= i 3))
				((& (ego onControl origin) cRed)			(= i 4))
				((& (ego onControl origin) cMagenta)	(= i 5))
				((& (ego onControl origin) cBrown)		(= i 6))
				(else												(= i 0))
			)
			(ego setLoop i)

			(if (< machineSpeed pcAT)
				(return)
			)
			(aSpotlight
				posn			[spotlightX i] spotlightY,
				setCel		i,
			)
			(aFloor
				posn			[spotlightX i] floorlightY,
				setCel		i,
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said '/strip, dance')
					(Said 'strip, dance'))
				(self changeState startDancing)
			)

			((Said 'talk')
				(Print "You begin,
					\"Unaccustomed as I am to public speaking...\"")
				(Print "The crowd roars its disapproval.
					They're not hear for a lecture, Larry.
					This is show biz!")
			)

			((Said 'look>')
				(cond
					((Said '[/room, stage, people, man, woman]')
						(Print "You are all alone on a large stage and runway,
							surrounded by men who expected to see a beautiful
							woman.")
						(Print "(Now you understand the term \"flop sweat!\")" LOW)
					)
				)
			)
		)
	)


	(method (changeState newState &tmp i)
		(ShowState self newState 1 ltRed)

		(switch (= state newState)

			(intro
			)


			;**		Larry was caught backstage with his plucking his Cherri.
			;**	Bring him on stage.

			(timpRoll4Larry
				(Print "The announcer's voice booms over the
					public address system,
					\"And now, lady and gentlemen,
					the moment you've all been waiting for:
					the star of our show, Miss Cherri Tart!\""
					BIG)
				(aCurtain
					setCycle			EndLoop self,
				)
				(delay 3)
			)

			(somethingsDone
			)

			(ohMyGawd
				(aCurtain			stopUpd)
				(Print (Format @string "\"%s\"" expletive)
					#font		BIG_FAT_18)
				(Print "You scream, \"People!
					Hundreds of people!
					And every one of them staring at me!!\"")
				(Print "You're caught, Larry! And redhanded, too!
					And, wearing Cherri's costume too!!")
				(delay 3)
			)

			(missCherri
				(Print "\"Lady and gentlemen,
					the star of our show, Miss Cherri Tart!\""
					BIG)
				(Print "In your hurry to dress in the darkness backstage,
					you must have grabbed the wrong clothes!")
				(Print "(So THAT'S why your underwear was such a tight fit!)" LOW)
				(delay 3)
			)

			(wheresCherri2
				(Print "\"Our star, Cherri Tart!\""
					HIGH)
				(delay 3)
			)

			(bringOutLarry
				(Print "\"CHERRI!\"" HIGH)
				(Print "You walk to the beginning of the runway.")
				(ego
					setPri			RELEASE,
					setMotion		MoveTo 36 runwayY self,
				)
			)

			(egoInPlace
				(Print "Well, Larry, you've done it again!
					Now what are you going to do?")
				(= egoState WAITING_TO_DANCE)
				(User canInput TRUE)
				(music				stop)
				(ego
					cel				0,
				)
				(aCurtain
					setCycle			BegLoop self,
				)
			)

			(curtainClosed
				(aCurtain			stopUpd)
				(delay 3)
			)

			(nowWhat
				(Print "You are mortified!
					How you wish you had paid more attention
					when your mother enrolled
					you in that pre-school ballet and gymnastics class!"
					BIG)
				(delay 10)
			)


			;**		Here we wait for the player to type in "dance";
			;**	If they don't, we move on to this section.

			(booHiss
				(music
					number			sBoosWhistlesCatcalls,
					loop				FOREVER,
					play,
				)
				(Print "\"Boo.\""
					HIGH)
				(Print "\"Hiss.\""
					LOW)
				(Print "\"Move it or lose it!\"")
				(delay 3)
			)

			(getOff
				(Print "\"Get off the stage!\""
					HIGH)
				(Print "\"Who's got the tomatoes?\""
					LOW)
				(= cycles (Random 2 8))
			)

			(initVeggies
				(HandsOff)
				(for ((= i 1)) (<= i 5) ((++ i))
					([aVeggie i]
						view				vRoom,
						setCycle			Forward,
						setStep			6 6,
						setScript		(VeggieScript new),
					)
				)
				(delay 8)
			)

			(UBdying
				(Print "Too bad, Larry.
					You feel you could die of embarrassment."
					#dispose
					BIG)
				(delay 3)
			)

			(deadOfEmbarrassment
				(cls)
				(for ((= i 1)) (<= i 5) ((++ i))
					(([aVeggie i] script) changeState VeggieDone)
				)
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vLdeadEmbarrassment,
					register 	(Format @string
										"In fact, just for purposes of argument,
										you ARE dead of embarrassment!"),
					next			(Format @string2 "\"Feets don't fail me now!\""),
				)
			)


			;**	They were smart enough to type in dance.  Here goes:

			(startDancing
				(= egoState UNDER_LIGHTS)
				(HandsOff)
				(Ok)
				(theGame changeScore 43)
				(Print "Summoning up all your courage,
					you begin to wiggle your way down the runway."
					BIG)
				(aSpotlight
					setLoop			lSpotlights,
					setPri			14,
					ignoreActors,
					init,
				)
				(aFloor
					setLoop			lFloorlight,
					setPri			7,
					ignoreActors,
					init,
				)
				(ego
					setMotion		MoveTo [spotlightX brown] runwayY self,
				)
				(= cycles 4)
			)

			(cueTheBand
				(music
					number			sLarryDancing,
					loop				1,
					play				self,
				)
			)

			(endOfRunway
				(ego
					setMotion MoveTo [spotlightX blue] runwayY self,
				)
			)

			(throwMoney
				(Print "The audience must also appreciate your talents as
					they begin to throw dollar bills_-- at you!"
					BIG
					#dispose)
				([aMoney 1]
					setScript		(MoneyScript new),
				)
				(ego
					setMotion MoveTo [spotlightX cyan] runwayY self,
				)
			)

			(throwMoney2
				([aMoney 2]
					setScript		(MoneyScript new),
				)
				([aMoney 3]
					setScript		(MoneyScript new),
				)
				(ego
					setStep			1 1,
					setMotion MoveTo [spotlightX brown] runwayY self
				)
			)

			(throwAll
				(cls)
				(Print "Look, Larry!
					They're all throwing money at you.
					They like you! They REALLY like you!!"
					BIG
					#time 6
					#dispose)
				([aMoney 4]
					setScript		(MoneyScript new),
				)
				([aMoney 5]
					setScript		(MoneyScript new),
				)
				(= register 0)
				(self cue)
			)

			(danceYourHeartOut
				(ego
					setMotion MoveTo (Random [spotlightX blue] [spotlightX brown]) runwayY self,
				)
				(if (>= (/ machineSpeed 10) (++ register))
					(-- state)
				)
			)

			;**	We park here until the music ends.

			(fade2black
				(music 
					loop			1,
					play, 
				)
				(curRoom drawPic blackPicture IRISIN)
				(cast eachElementDo #hide)
				(for ((= i 1)) (<= i 5) ((++ i))
					(([aMoney i] script) changeState MoneyDone)
				)
				(= cycles 20)
			)

			(changeRooms
				(music fade)
				(Print "Time passes.")
				(Print "Time in which you dance and dance,
					meanwhile picking up many, many one-dollar bills.")
				(Print "You dance until your legs just can't take it any longer!")
				(Print "(Which isn't really that long considering you are
					wearing a pair of high heels three sizes too small!)"
					#at 10 144
					#width 290)
				(Load VIEW vLziegfield)
				(= normalEgoView vLziegfield)
				(= egoState WEARING_ZIEGFIELD)
				(= showroomState SRstageDoorUnlocked)
				(= dollars 500)
				(Format ((Inventory at iMoney) name) "500 Dollar Bills")
				((Inventory at iMoney) view ivOnes)
				(ego get iMoney)
				(theGame setSpeed oldGameSpeed)
				(curRoom newRoom 420)
			)
		)
	)
)

(instance aActor1 of Actor)
(instance aActor2 of Actor)
(instance aActor3 of Actor)
(instance aActor4 of Actor)
(instance aActor5 of Actor)

(instance aSpotlight of Prop
	(properties
		view 				vRoom
		x					999
		y					999
	)
)

(instance aFloor of Prop
	(properties
 		view 				vRoom
		x					999
		y					999
	)
)

(instance aCurtain of Prop
	(properties
		view 				vRoom
		loop 				lCurtain
		x					15
		y					63
		cycleSpeed		1
	)
	(method (init)
		(super init)
		(self
			setPri			2,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance VeggieScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(VeggieInit
				(= cycles (Random 1 9))
			)

			(VeggieStart
				(client
					setLoop			(Random lVeggies lTomatoes),
					posn				(Random 8 290) (Random 144 157),
					setMotion		JumpTo (ego x) (- (ego y) (Random 0 33)) self,
				)
				(-- state)
			)

			(VeggieDone
				(client				dispose)
				(self					dispose)
			)
		)
	)
)
(instance MoneyScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(MoneyInit
				(client
					view				vRoom,
					setLoop			lMoney,
					setCycle			Forward,
					setStep			7 7,
				)
				(= cycles (Random 1 9))
			)

			(MoneyStart
				(client
					posn				(Random 8 290) (Random 144 157),
					setMotion		JumpTo (ego x) (- (ego y) (Random 0 33)) self,
				)
				(-- state)
			)

			(MoneyDone
				(client				dispose)
				(self					dispose)
			)
		)
	)
)


