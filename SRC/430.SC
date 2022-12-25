;**
;**	Logics for room 430 -- Cherri On Stage
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 19, 1989
;**


(script#				430)

(public
	rm430				0
)

(include "430.sh")


;**		This is the dance sequence Larry sees when he walks in from
;**	the lobby.


(enum												;**	RoomScript state values
	startD1
	startD2
	startD3
	startD4
	startD5
	stopD3
	stopD4
	ripple5
	ripple4
	ripple3
	ripple2
	ripple1
	ripple2a
	ripple3a
	ripple4a
	ripple5b
	ripple4b
	ripple3b
	ripple2b
	rockette1
	rockette2
	rockette3
	rockette4
	rockette5
	moveEmOff
	theyreOffstage
	timpRoll
	lightsOn
	openTrapdoor
	raiseYourCherri
	aCherriOnTop
	kickHigher
	startHerSpin
	youBroughtHer
	youSpeedHer
	youSpinHer
	youFlasher
	turnOffSpots
	youSlowHer
	endHerSpin
	spinCycleDone
	allClap
	shesGone
	strikeSpot
	goodShow
	changeToLobby
)


(enum												;**	MoneyScript state values
	MoneyInit
	MoneyStart
	MoneyDone
)


(local
	aHoist
	aTrapdoor
	aCherri
	[aMoney  6]
	[aDancer 6]
	dancerPosn = [	  0   0
						 54  74
						 41  78
						 26  83
						 11  88
						 -4  93
						  0   0	]
)


(instance rm430 of Room
	(properties
		picture		430
		horizon		1
	)


	(method (init &tmp i)
		(= showroomState SRshowDone)
		(= oldEgoState egoState)
		(= egoState curRoomNum)

		(HandsOff)

		(Load VIEW vCherriDancingSmall)

		(Load SOUND	sCherriDancing)
		(Load SOUND sApplause)
		(Load SOUND sTimpaniRoll)

		(Load SCRIPT JUMP)

		(Load PICTURE 431)

		(music
			number			sShowroomDancing,
			loop				FOREVER,
			play,
		)

		(super init)

		(aCurtain	ignoreActors, init)

		(aActor1 ignoreActors, init)
		(aActor2 ignoreActors, init)
		(aActor3 ignoreActors, init)
		(aActor4 ignoreActors, init)
		(aActor5 ignoreActors, init)

		(= oldGameSpeed (theGame setSpeed 6))

		(= [aDancer 1] 	aActor1)
		(= [aDancer 2] 	aActor2)
		(= [aDancer 3] 	aActor3)
		(= [aDancer 4] 	aActor4)
		(= [aDancer 5] 	aActor5)

		(aMoney1 ignoreActors, init)
		(aMoney2 ignoreActors, init)
		(aMoney3 ignoreActors, init)
		(aMoney4 ignoreActors, init)
		(aMoney5 ignoreActors, init)

		(= [aMoney  1] 	aMoney1)
		(= [aMoney  2] 	aMoney2)
		(= [aMoney  3] 	aMoney3)
		(= [aMoney  4] 	aMoney4)
		(= [aMoney  5] 	aMoney5)

		(= aTrapdoor		aActor3)			;**	reuse those girls
		(= aHoist			aActor4)
		(= aCherri			aActor5)

		(ego
			posn				999 999,
			init,
			hide,
		)

		(for ((= i 1)) (<= i 5) ((++ i))
			([aDancer i]
				view			vDancers,
				posn			[dancerPosn (* i 2)]	[dancerPosn (+ 1 (* i 2))],
				setPri		3,
				setStep		1 1,
			)
		)

		(self
			setScript 		RoomScript
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)

	(method (changeState newState &tmp i)
		(ShowState self newState 1 ltRed)

		(switch (= state newState)

			(startD1
				([aDancer 1] setCycle EndLoop self)
			)

			(startD2
				([aDancer 1] cel 0, setCycle EndLoop)
				([aDancer 2] setCycle EndLoop self)
			)

			(startD3
				([aDancer 1] cel 0, setCycle EndLoop )
				([aDancer 2] cel 0, setCycle EndLoop )
				([aDancer 3] setCycle EndLoop self)
			)

			(startD4
				([aDancer 1] cel 0)
				([aDancer 2] cel 0, setCycle EndLoop )
				([aDancer 3] cel 0, setCycle EndLoop )
				([aDancer 4] setCycle EndLoop self)
			)

			(startD5
				(Print "You quickly make your way through a crowd of men
					and take one of the few remaining seats near the
					rear of the showroom."
					#at 10 144
					#width 290
					#dispose)
				([aDancer 2] cel 0)
				([aDancer 3] cel 0, setCycle EndLoop )
				([aDancer 4] cel 0, setCycle EndLoop )
				([aDancer 5] setCycle EndLoop self)
			)

			(stopD3
				([aDancer 3] cel 0)
				([aDancer 4] cel 0, setCycle EndLoop )
				([aDancer 5] cel 0, setCycle EndLoop self)
			)

			(stopD4
				([aDancer 4] cel 0)
				([aDancer 5] setCycle EndLoop self)
			)

			(ripple5
				([aDancer 5] cel 0, setCycle EndLoop self)
			)

			(ripple4
				([aDancer 4] cel 0, setCycle EndLoop self)
			)

			(ripple3
				([aDancer 3] cel 0, setCycle EndLoop self)
			)

			(ripple2
				([aDancer 2] cel 0, setCycle EndLoop self)
			)

			(ripple1
				([aDancer 1] cel 0, setCycle EndLoop self)
			)

			(ripple2a
				([aDancer 2] setCycle EndLoop self)
			)

			(ripple3a
				([aDancer 3] setCycle EndLoop self)
			)

			(ripple4a
				([aDancer 4] setCycle EndLoop self)
			)

			(ripple5b
				([aDancer 5] setCycle EndLoop self)
			)

			(ripple4b
				([aDancer 4] setCycle EndLoop self)
			)

			(ripple3b
				([aDancer 3] setCycle EndLoop self)
			)

			(ripple2b
				([aDancer 2] setCycle EndLoop self)
			)

			(rockette1
				(cls)
				([aDancer 1] setCycle EndLoop self)
			)

			(rockette2
				([aDancer 1] setCycle EndLoop self)
				([aDancer 2] setCycle Forward)
			)

			(rockette3
				([aDancer 1] setCycle EndLoop self)
				([aDancer 3] setCycle Forward)
			)

			(rockette4
				([aDancer 1] setCycle EndLoop self)
				([aDancer 4] setCycle Forward)
			)

			(rockette5
				([aDancer 1] setCycle Forward)
				([aDancer 5] setCycle Forward)
				(= cycles 55)
			)

			(moveEmOff
				(for ((= i 1)) (<= i 5) ((++ i))
					([aDancer i] setMotion MoveTo 119 52)
				)
				(= cycles 66)
			)

			(theyreOffstage
				(music			fade)
				(= cycles 66)
			)

			(timpRoll
				(music
					number		sTimpaniRoll,
					loop			FOREVER,
					play,
				)
				(Print "The announcer's voice booms over the
					public address system,
					\"And now, lady and gentlemen,
					the moment you've all been waiting for:
					the star of our show, Miss Cherri Tart!\""
					BIG)
				([aDancer 1] 	dispose)
				([aDancer 2] 	dispose)
				(aTrapdoor
					view 				vRoom,
					setLoop 			lTrapdoor,
					posn 				167 97,
					setPri			2,
					hide,
				)
				(aHoist
					view 				vRoom,
					setLoop 			lHoist,
					posn				148 hoistDownY,
					setPri			3,
					hide,
				)
				(aCherri
					view 				vCherriDancingSmall,
					setLoop 			lStartSpin,
					setCel 			0,
					posn				149 cherriDownY,
					setPri			4,
					hide,
				)
				(= cycles 22)
			)

			(lightsOn
				(curRoom drawPic 431 DISSOLVE)
				(aSpotlight
					loop 				lBigLight,
					setCel 			1,
					posn 				160 45,
					setPri			1,
					ignoreActors,
					init,
					stopUpd,
				)
				(aFloor
					loop 				lBigLightOnFloor,
					setCel 			1,
					posn 				152 102,
					setPri			6,
					ignoreActors,
					init,
					stopUpd,
				)
				(aHoist 				show)
				(aCherri				show)
				(= cycles 11)
			)

			(openTrapdoor
				(aTrapdoor
					show,
					setMotion		MoveTo 149 97 self,
				)
			)

			(raiseYourCherri
				(aTrapdoor			stopUpd)
				(aCherri
					setMotion		MoveTo 149 cherriUpY
				)
				(aHoist
					setMotion		MoveTo 148 hoistUpY self,
				)
			)

			(aCherriOnTop
				(aHoist				stopUpd)
				(music
					number			sCherriDancing,
					loop				FOREVER,
					play,
				)
				(aCherri
					setLoop			lKick,
					setCycle			Forward,
				)
				(= cycles 33)
			)

			(kickHigher
				(aCherri
					setLoop			lKickHigh,
				)
				(= cycles 33)
			)

			(startHerSpin
				(aCherri
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(youBroughtHer
				(aCherri
					setLoop			lSpin,
					setCycle			Forward,
				)
				(Print "Evidently the men in the audience like Cherri's 
					act, as they begin to toss dollar bills onto the stage."
					#at 10 144
					#width 290
					#dispose)
				(for ((= i 1)) (<= i 5) ((++ i))
					([aMoney i] setScript (MoneyScript new))
				)
				(self cue)
			)

			(youSpeedHer
				(= cycles (* 2 (NumCels aCherri)))
			 	(if ( > speed 1)				;**	fall thru when speed == 1
					(theGame setSpeed (-- speed))
					(-- state)
				)
			)

			(youSpinHer
				(cls)
				(aCherri
					setCycle			Forward,
				)
				(= cycles (+ 7 (* machineSpeed 2)))
			)

			(youFlasher
				(if (< machineSpeed pcAT)
					(self cue)
				else
					(aSpotlight
						setCycle			Forward,
					)
					(aFloor
						setCycle			Forward,
					)
					(= cycles (+ 7 (* machineSpeed 2)))
				)
			)

			(turnOffSpots
				(aSpotlight
					setCel			1,
					stopUpd,
				)
				(aFloor
					setCel			1,
					stopUpd,
				)
				(self cue)
			)

			(youSlowHer
				;**	wait 1 loop if slow machine or 2 loops if faster than an AT
				(= cycles (* (+ 1 (> machineSpeed pcAT)) (NumCels aCherri)))
				(if (< speed 6)				;**	fall thru when speed = 6
					(theGame setSpeed (++ speed))
					(-- state)
				)
			)

			(endHerSpin
				(aCherri
					setLoop			lEndSpin,
					setCycle			EndLoop self,
				)
			)

			(spinCycleDone
				(aCherri
					setCel			LastCel,
				)
				(music				fade)
				(delay 4)
			)

			(allClap
				(Print "\"Isn't she wonderful, lady and gentlemen?!\"
					screams the announcer.
					\"Let's really hear it for our own little Cherri Tart!!\""
					BIG)
				(music
					number			sApplause,
					loop				FOREVER,
					play,
				)
				(aHoist
					setMotion		MoveTo 148 hoistDownY,
				)
				(aCherri
					setMotion		MoveTo 149 cherriDownY self,
				)
				(for ((= i 1)) (<= i 5) ((++ i))
					(([aMoney i] script) changeState MoneyDone)
				)
			)

			(shesGone
				(music fade)
				(Print "\"That concludes tonight's two-hour spectacle,
					`Nontoonyt For You!'
					You must clear the auditorium now,
					as our second show begins shortly.
					Good seats are still available, so tell your friends!\""
					BIG)
				(aTrapdoor
					setMotion		MoveTo 167 97 self,
				)
			)

			(strikeSpot
				(curRoom drawPic 430 DISSOLVE)
				(aTrapdoor			dispose)
				(aSpotlight			dispose)
				(aFloor				dispose)
				(RedrawCast)
				(Print "He concludes with a trite,
					\"Good night, and walk safely on your way home.\""
					BIG)
				(delay 3)
			)

			(goodShow
				(Print "What a show, eh, Larry?")
				(Print "(Too bad you came in so late;
					you only saw the last minute!)" LOW)
				(delay 2)
			)

			(changeToLobby
				(theGame setSpeed oldGameSpeed)
				(= egoState oldEgoState)
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


(instance aMoney1 of Actor)
(instance aMoney2 of Actor)
(instance aMoney3 of Actor)
(instance aMoney4 of Actor)
(instance aMoney5 of Actor)

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
					setMotion		JumpTo (aCherri x) (- (aCherri y) (Random 0 33)) self,
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


