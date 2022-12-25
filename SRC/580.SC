;**
;**	Logics for room 580 -- Patti gets caught by the Les-bos
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	October 18, 1989
;**


(script#				580)

(public
	rm580				0
)


(define	vRoom							580)
	(define	lNet					0)
	(define	lLogOnly				1)

(define	vProom						581)
	(define	lPonLog				0)
	(define	lNetOverP			1)
	(define	lPsinking			2)
	(define	lDrowningWithNet	3)

(define	vAmazon1						582)
(define	vAmazon2						583)
	(define	lWalkingWest	 	0)
	(define	lJumping				1)
	(define	lIntoWater			2)
	(define	lLeavingWater		3)
	(define	lWading				4)
	(define	lWalkingNW			5)
	(define	lWalkingNWsmall	6)

(define	sNetDropping				580)
(define	sWomenDropping				581)
;(define	sCannibalVillage			585)


(enum												;**	RoomScript state values
	intro
	youSee
	dropNet
	netAtP
	netted
	drowningWithNet
	oneJumpsIn
	oneAtWater
	oneUnderwater
	whatAreTheyDoing
	oneLeavesWater
	walk1ToShore
	ohOH
	oneOnShore
	otherHand
)


(enum												;**	Woman2Script state values
	twoJumpsIn
	twoAtWater
	twoUnderwater
	twoLeavesWater
	walk2ToShore
	twoOnShore
	allDone
)


(instance rm580 of Room
	(properties
		picture		580
	)

	(method (init)
		(HandsOff)

		(Load VIEW vRoom)
		(Load VIEW vAmazon1)
		(Load VIEW vAmazon2)

		(Load SOUND sNetDropping)
		(Load SOUND sWomenDropping)
		(Load SOUND sCannibalVillage)

		(Load SCRIPT JUMP)

		(super init)

		(ego
			view				vProom,
			setLoop			lPonLog,
			setStep			2 2,
			posn				370 10,
			init,
			setCel			1,
		)

		(self
			setScript 		RoomScript
		)
	)
)


(instance RoomScript of Script
	(method (changeState newState &tmp [string 100])
		(ShowState self newState 1 green)

		(switch (= state newState)

			(intro
				(ego
					setMotion		MoveTo 264 53 self,
				)
			)

			(youSee
				(Print "You finally make your way past all the obstacles
					and arrive at a pleasant little clearing.
					This might be a good spot to take a short rest.
					You look for a place to beach your trusty log." WIDE)
				(ego
					viewer			HeadTurner,
					setMotion		MoveTo 130 97 ,
				)
				(music				fade)
				(= cycles 60)
			)

			(dropNet
				(music
					number			sNetDropping,
					loop				1,
					play,
				)
				(aWoman1
					ignoreHorizon,
					ignoreActors,
					view				vRoom,
					setLoop			lNet,
					setPri			8,
					setStep			1 1,
					setMotion		JumpTo 128 61 self,
					init,
				)
			)

			(netAtP
				(Print "LOOK OUT!!" HIGH #dispose)
				(aWoman1
					view				vAmazon1,
					setLoop			lJumping,
					posn				94 -2,
				)
				(ego
					viewer			FALSE,
					setLoop			lNetOverP,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(netted
				(ego
					setLoop			lPsinking,
					cel				0,
					setCycle			EndLoop self
				)
			)

			(drowningWithNet
				(ego
					setLoop			lDrowningWithNet,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(oneJumpsIn
				(music
					number			sWomenDropping,
					loop				1,
					play,
				)
				(cls)
				(ego
					setCycle			EndLoop,
				)
				(aWoman2				init)
				(aWoman1
					setCycle			Forward,
					setStep			1 1,
					setMotion		JumpTo 114 97 self,
				)
			)

			(oneAtWater
				(ego
					hide,
					view				vRoom,
					setLoop			lLogOnly,
					cel				0,
					posn				89 118,
				)
				(aWoman1
					setLoop			lIntoWater,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(oneUnderwater
				(ego
					setCycle			EndLoop,
					setMotion		MoveTo -47 164,
					show,
				)
				(= cycles 11)
			)

			(whatAreTheyDoing
				(Print "What have they done to you, Patti?!")
				(= cycles 22)
			)

			(oneLeavesWater
				(Woman2Script 		cue)
				(aWoman1
					setLoop			lLeavingWater,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(walk1ToShore
				(music
					number			sCannibalVillage,
					loop				FOREVER,
					play,
				)
				(aWoman1
					setLoop			lWading,
					setCycle			Walk,
					setStep			3 2,
					setMotion		MoveTo 44 77 self,
				)
				(= cycles 15)
			)

			(ohOH
				(Print (Format @string
					"%s You've been captured by Nontoonyt's famous
					tribe of Amazonian women%s cannibal natives!!
					Now what will you do?
					How will you ever find your Larry?"
					expletive
					(if (>= filthLevel DIRTY) { lesbian} else {}))
					WIDE)
			)

			(oneOnShore
				(aWoman1
					setLoop			lWalkingNW,
					setCycle			Walk,
					setMotion		MoveTo -20 60,
				)
				(= cycles 11)
			)

			(otherHand
				(Print "(On the other hand, where do they find such stylish
					outfits way out here in this wilderness?)" LOW)
			)
		)
	)
)

(instance aWoman1 of Actor
	(properties
		view				vRoom
		x					128
		y					-2
		illegalBits		0
	)
)

(instance aWoman2 of Actor
	(properties
		view				vAmazon2
		x					161
		y					-11
		illegalBits		0
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			setScript		Woman2Script,
		)
	)
)

(instance Woman2Script of Script
	(method (changeState newState)
		(ShowState self newState 2 green)

		(switch (= state newState)

			(twoJumpsIn
				(aWoman2
					setLoop			lJumping,
					setCycle			Forward,
					setStep			1 1,
					setMotion		JumpTo 141 94 self,
				)
			)

			(twoAtWater
				(aWoman2
					setLoop			lIntoWater,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(twoUnderwater
			;**	wait for RoomScript to cue
			)

			(twoLeavesWater
				(aWoman2
					setLoop			lLeavingWater,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(walk2ToShore
				(aWoman2
					setLoop			lWading,
					setCycle			Walk,
					setStep			3 2,
					setMotion		MoveTo 66 73 self,
				)
			)

			(twoOnShore
				(aWoman2
					setLoop			lWalkingNW,
					setMotion		MoveTo -20 60 self,
				)
			)

			(allDone
				(curRoom newRoom 585)
			)
		)
	)
)

(instance HeadTurner of Code
	(method (doit)
		(if (not (Random 0 3))
			(ego setCel	(Random 0 4))
		)
	)
)

