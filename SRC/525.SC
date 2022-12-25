;**
;**	Logics for room 525 -- falling off a cliff Long Shot
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 3, 1989
;**


(script#				525)

(public
	rm525				0
)


(define	vRoom							525)
	(define	lFalling525			4)
	(define	lHose					5)

;(define	s1Boing						003)
(define	s2Boing						526)
(define	s3Boing						527)
;(define	vPdeadFalling				814)


(enum												;**	RoomScript state values
	intro
	youLowerYourself
	goDown1a
	goUp1a
	goDown2a
	goDown2b
	goUp2b
	goUp2a
	goDown3a
	goDown3b
	goDown3c
	goUp3c
	goUp3b
	goUp3a
	goDown4a
	goDown4b
	goDown4c
	goDown4d
	breakHose
	changeRooms
	UBfalling
	UBdying
	UBdead
)


(local
	[string  40] 
	[string2 22]
)


(instance rm525 of Room
	(properties
		picture		525
		horizon		1
	)


	(method (init)
		(Load VIEW vRoom)

		(Load SOUND s1Boing)
		(Load SOUND s2Boing)
		(Load SOUND s3Boing)
		(Load SOUND sFalling)

		(HandsOff)

		(cls)										;**	clear window from 520, if any
		(= oldGameSpeed (theGame setSpeed 6))

		(super init)

		(ego
			view				vRoom,
			setLoop			(if (== egoState FALLING) lFalling525 else 0),
			setCel			0,
			setStep			0 7,
			setMotion		FALSE,
			setCycle			(if (== egoState FALLING) Forward else FALSE),
			posn 				142 13,
			cycleSpeed		1,
			illegalBits		0,
			ignoreActors,
			init,
			put				iPantyhose LIMBO,
		)

		(self
			setScript 		RoomScript,
		)
	)
)


(instance RoomScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(intro
				(if (!= egoState FALLING)
					(= cycles 10)
				else
					(music
						number			sFalling,
						loop				1,
						play,
					)
					(ego
						setMotion		MoveTo 144 18 self,
					)
					(= state (- UBfalling 1))
				)
			)

			(youLowerYourself
				(Print "You lower yourself
					(an experience not wholly unfamiliar to you)
					over the sheer side of the canyon wall,
					your full weight supported only by a pair of
					run-filled pantyhose!")
				(Print "(How you wish you'd changed to a clean pair this
					morning!)" LOW)
				(= cycles 20)
			)

			(goDown1a
				(music
					stop,
					number			s1Boing,
				 	loop				1,
					play,
				)
				(ego
					setCycle			EndLoop self,
				)
			)

			(goUp1a
				(ego
					setCycle			BegLoop self,
				)
			)

			(goDown2a
				(music
					stop,
					number			s2Boing,
				 	loop				1,
					play,
				)
				(ego
					setCycle			EndLoop self,
				)
			)

			(goDown2b
				(ego
					setLoop			1,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(goUp2b
				(ego
					setCycle			BegLoop self,
				)
			)

			(goUp2a
				(ego
					setLoop			0,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(goDown3a
				(music
					stop,
					number			s3Boing,
				 	loop				1,
					play,
				)
				(ego
					setCycle			EndLoop self,
				)
			)

			(goDown3b
				(ego
					setLoop			1,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(goDown3c
				(ego
					setLoop			2,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(goUp3c
				(ego
					setCycle			BegLoop self,
				)
			)

			(goUp3b
				(ego
					setLoop			1,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(goUp3a
				(ego
					setLoop			0,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(goDown4a
				(music
					stop,
					number			s3Boing,
				 	loop				1,
					play,
				)
				(ego
					setCycle			EndLoop self,
				)
			)

			(goDown4b
				(ego
					setLoop			1,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(goDown4c
				(ego
					setLoop			2,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(goDown4d
				(ego
					setLoop			3,
					setCel			0,
					setCycle			EndLoop self,
				)
			)

			(breakHose
				(ego
					setLoop			lFalling525,
					setPri			RELEASE,
					posn				143 114,
					setCycle			Forward,
				)
				(aHose
					ignoreActors,
					init,
				)
				(Print "Oh, no! Your pantyhose broke!!" HIGH #draw)
				(music
					stop,
					number			sFalling,
				 	loop				1,
					play,
				)
				(curRoom newRoom 530)
			)


			;**	This is the unsuccessful route:

			(UBfalling
				(ego
					setMotion		theJump self,
				)
			)

			(UBdying
				(Print "Ow!")
				(ego hide)
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadFalling,
					register 	(Format @string "Really, Patti!
										Everyone likes to make a good impression, 
										but..."),
					next			(Format @string2 "Busted Flat"),
				)
			)
		)
	)
)

(instance aHose of Prop
	(properties
		view					vRoom
		loop					lHose
		x						142
		y						13
	)
)

(instance theJump of Jump
	(method (init)
		(super init ego RoomScript)
		(self
			yStep 	1,
			y 			183,
		)
	)
)

