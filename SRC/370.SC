;**
;**	Logics for room 370 -- Fat City Locker Room
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	December 28, 1989
;**


(script#				370)

(public
	rm370				0
)


(define	vLockerRoom					370)
	(define lPeopleR				0)
	(define lPeopleL				1)
	(define lLocker				2)

;(define	vLnaked						702)
;(define	vLtowel						703)
;(define	vLsweats						704)

;(define	vLhunk						720)
;(define	vLnakedHunk					722)
;(define	vLtowelHunk					723)
;(define	vLsweatsHunk				724)


(define	enterShower					(& (ego onControl) cGreen))
(define  nearShower					(& (ego onControl) cBlue))
(define	enterWeightRoom			(& (ego onControl) cRed))
(define	nearWeightRoom				(& (ego onControl) cCyan))
(define	nearlobby					(& (ego onControl) cMagenta))
(define	hot							(& (ego onControl) cWhite))
(define	warmer                  (&	(ego onControl) cGray))
(define	warm							(& (ego onControl) cLtBlue))
(define	nearLocker					(& (ego onControl) cBrown))
(define  lockerOpen					(< (aLocker y) 999))

(define	lobbyBlock					cLtRed)
(define	showerBlock					cYellow)
(define	weightRoomBlock			cLtMagenta)

(define	LOCKER_LOCATION			88 65)


(enum												;**	Man3Script state values
	Man3Init
	Man3Start
	Man3Delay
	Man3Done
)
(enum												;**	Man2Script state values
	Man2Init
	Man2Start
	Man2Delay
	Man2Done
)
(enum												;**	Man1Script state values
	Man1Init
	Man1Start
	Man1Delay
	Man1Done
)
(enum												;**	RoomScript state values
	intro
	beginStrip
	smallDelay
	goBackUp
	stripDone
)


(local
	hisCombination1 
	hisCombination2 
	hisCombination3 
	seenMsg
	howFar
	[string 200]
)


(instance rm370 of Room
	(properties
		picture		370
		horizon		54
	)

	(method (init)
		(Load VIEW (+ vLnakedModest	egoIsHunk))
		(Load VIEW (+ vLnaked			egoIsHunk))
		(Load VIEW (+ vLsweats			egoIsHunk))
		(Load VIEW (+ vLarry				egoIsHunk))

		(if (ego has iTowel)
			(Load VIEW (+ vLtowel		egoIsHunk))
			(Load VIEW iTowel)
		)

		(super			init)

		(if (> machineSpeed pc8088)
			(aMan1			init)
			(aMan2			init)
			(aMan3			init)
		)

		(aLocker			init)

		(self
			setScript		RoomScript,
		)

		(NormalEgo)
		(cond
			((== prevRoomNum 375)			;**	from the shower room
				(ego
					loop			facingSouth,
					posn			221 58,
				)
			)

			((== prevRoomNum 380)			;**	from the weight room
				(ego
					loop			facingSouth,
					posn			313 62,
				)
			)

			(else									;**	from the snack bar
				(= egoState WEARING_SUIT)
				(ego
					posn			307 179,
				)
			)
		)
		(ego
			view
				(switch egoState
					(NAKED_BUT_MODEST	(+ vLnakedModest	egoIsHunk))
					(NAKED				(+ vLnaked			egoIsHunk))
					(WEARING_TOWEL		(+ vLtowel			egoIsHunk))
					(WEARING_SWEATS 	(+ vLsweats			egoIsHunk))
					(else					(+ vLarry			egoIsHunk))
				),
			init
		)
	)

	(method (newRoom n)
		(if lockerOpen
			(SetFlg lockerRippedOff)
		)

		(if (and (== n 375)					;**	shower
					(== egoState WEARING_TOWEL)
					(ego has iTowel))
			(Print "You hang your towel just outside the shower room
				where you'll be able to reach it easily upon exit." WIDE)
			(PutInRoom iTowel 375)
		)

		(super newRoom n)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(switch egoState
			(NAKED
				(ego
					observeControl		lobbyBlock weightRoomBlock,
					ignoreControl		showerBlock,
				)
			)
			(NAKED_BUT_MODEST
				(ego
					observeControl		lobbyBlock weightRoomBlock,
					ignoreControl		showerBlock,
				)
			)
			(WEARING_SWEATS
				(ego
					observeControl		showerBlock lobbyBlock,
					ignoreControl		weightRoomBlock,
				)
			)
			(WEARING_TOWEL
				(ego
					observeControl		weightRoomBlock lobbyBlock,
					ignoreControl		showerBlock,
				)
			)
			(else
				(ego
					observeControl		weightRoomBlock showerBlock,
					ignoreControl		lobbyBlock,
				)
			)
		)

		(if (& (ego onControl) cLtCyan)			(ego setPri cyan))
		(if (& (ego onControl) cLtGreen)			(ego setPri RELEASE))

		(cond
			(enterShower
				(curRoom newRoom 375)
			)

			(nearShower
				(if (not seenMsg)
					(cond
						((== egoState WEARING_SUIT)
							(= seenMsg TRUE)
							(Print "You can't walk into a shower wearing your
								leisure suit!")
						)
						((== egoState WEARING_SWEATS)
							(= seenMsg TRUE)
							(Print "The sweatsuit would weigh a ton if you
								wore it into the shower!")
						)
					)
				)
			)

			(enterWeightRoom
				(curRoom newRoom 380)
			)

			(nearWeightRoom
				(if (not seenMsg)
					(cond
						((or	(== egoState NAKED)
								(== egoState NAKED_BUT_MODEST))
							(= seenMsg TRUE)
							(Print "You're far too modest to work out on those
								weight machines in the nude.
								In fact, you can hear women snickering because
								you're standing here in front of the open doorway!")
						)
						((== egoState WEARING_SUIT)
							(= seenMsg TRUE)
							(Print "In order to work out on the weight machines,
								you must change into a sweatsuit!")
							(if (not egoIsHunk)
								(Print "(And from the looks of that stomach
									you could certainly stand to spend a few hours
									in there!)" LOW)
							)
						)
						((== egoState WEARING_TOWEL)
							(= seenMsg TRUE)
							(Print "That's a nice towel, but it's not suitable
								garb for a man using exercise machines!")
						)
					)
				)
			)

			((== EAST (ego edgeHit))
				(= egoState NORMAL)
				(= normalEgoView (+ vLarry	egoIsHunk))
				(curRoom newRoom 360)
			)

			(nearlobby
				(if (not seenMsg)
					(cond
						((or	(== egoState NAKED)
								(== egoState NAKED_BUT_MODEST))
							(= seenMsg TRUE)
							(Print "While it appears you enjoy gallivanting around
								in the nude here in the locker room,
								Fat City's strictly prohibits naked people in the
								lobby!")
						)
						((== egoState WEARING_SWEATS)
							(= seenMsg TRUE)
							(Print "You can't go into the lobby wearing sweats!")
						)
						((== egoState WEARING_TOWEL)
							(= seenMsg TRUE)
							(Print "Hey! Take off that towel, and put on your
								leisure suit if you want to visit the lobby!")
						)
					)
				)
			)

			(else
				(= seenMsg FALSE)
			)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((or	(Said '/combination')
					(Said 'yes')
					(Said 'unlock, use, open/locker, door'))
				(if lockerOpen
					(ItIs)
				else
					(Print "You clear the mechanism by quickly spinning 
						the lock several turns to the right.
						Which three numbers do you wish to try?")
						
					(while (<= hisCombination1 0)
						(= hisCombination1 (GetNumber {First number:}))
					)
					(while (<= hisCombination2 0)
						(= hisCombination2 (GetNumber {Second number:}))
					)
					(while (<= hisCombination3 0)
						(= hisCombination3 (GetNumber {Third number:}))
					)

					(Printf "You carefully turn the dial to the right 
						until you reach the number %d, 
						then left all the way around past %d until you reach %d,
						then right again stopping on %d. 
						You lift the handle of the locker."
						hisCombination1
						hisCombination1
						hisCombination2
						hisCombination3
					)

					(cond
						((not nearLocker)
							(Print "Darn. Nothing happens. 
								This must not be the correct locker.")
						)
						((or	(!= hisCombination1 lockerCombination1)
								(!= hisCombination2 lockerCombination2)
								(!= hisCombination3 lockerCombination3))
							(Print "Here you are at locker #69 and
								you don't know the combination!")
						)
						(else
							(if (not (TstFlg scoredLocker))
								(SetFlg scoredLocker)
								(theGame changeScore 100)
								(Print "You did it!")
							else
								(Print "The locker opens.")
							)
							(aLocker
								posn			LOCKER_LOCATION
							)
						)
					)
					(= hisCombination1 0)
					(= hisCombination2 0)
					(= hisCombination3 0)
				)
			)

			((or	(Said 'unknownNumber/')
					(Said '/unknownNumber')
					(Said '//unknownNumber'))
				(Print "Do you want to dial the combination of a locker?")
			)

			((Said 'lock, close/locker, door')
				(cond
					((not lockerOpen)
						(ItIs)
					)
					((not nearLocker)
						(NotClose)
					)
					(else
						(Ok)
						(aLocker
							posn			1111 1111,
						)
					)
				)
			)

			((Said 'remove, get/photo')
				(Print "You have all the photos of Tom Selleck that you need.")
				(Print "(Zero.)" LOW)
			)

			((or	(Said 'undress') 
					(Said 'wear/nothing')	
					(Said 'get/naked')
					(Said 'strip')
					(Said '(change<(out<of), from), (take<off), remove/
						suit, towel, sweats, clothes'))
				(cond
					((or	(== egoState NAKED)
							(== egoState NAKED_BUT_MODEST))
						(Print "You're as naked as you're going to get!")
					)
					((not nearLocker)
						(Print "Not here. Find your locker.")
					)
					((not lockerOpen)
						(Print "Your locker is still closed.")
					)
					(else
						(Format @string "You remove everything you are wearing
							and toss it in the locker.
							Congratulations!
							You're quickly turning this family-oriented
							entertainment software into an X-rated game!
							Next thing you know, you'll probably begin to enjoy
							walking around like this.
							THEN where will we be?!")
						(if ( < filthLevel DIRTY)
							(= egoState NAKED_BUT_MODEST)
						else
							(= egoState NAKED)
						)
						(self changeState beginStrip)
					)
				)
			)

			((or	(Said 'dressed<get')
					(Said 'get/dressed')
					(Said 'wear, get, (change<into, to), (put<on)/suit[<leisure]'))
				(cond
					((== egoState WEARING_SUIT)
						(Print "You're already wearing your leisure suit.")
					)
					((TstFlg wetBody)
						(Print "You can't change into your leisure suit 
							with water all over your body!")
					)
					((not nearLocker)
						(Print "You can't reach your clothes from here!")
					)
					((not lockerOpen)
						(Print "Your locker is still closed.")
					)
					((TstFlg lockerRippedOff)
						(Print "Your locker is empty!
							You didn't leave it open when you left before, did you?")
					)
					(else
						(Format @string "You return to your leisure suit,
							once again ready to face the world of women.")
						(= egoState WEARING_SUIT)
						(self changeState beginStrip)
					)
				)
			)

			((Said 'wear, get, (change<into), (put<on)/towel')
				(cond
					((== egoState WEARING_TOWEL)
						(Print "You're already wearing the towel.")
					)
					((not (ego has iTowel))
						(DontHave)
					)
					((not nearLocker)
						(Print "You would, but you really don't want to!")
					)
					((not lockerOpen)
						(Print "Your locker is still closed.")
					)
					((TstFlg lockerRippedOff)
						(Print "Your locker is empty!
							You didn't leave it open when you left before, did you?")
					)
					(else
						(Format @string "You carefully tie the beach towel around
							your waist, leaving your leisure suit
							in the locker with the sweatsuit.")
						(= egoState WEARING_TOWEL)
						(self changeState beginStrip)
					)
				)
			)

			((Said 'wear, get, (change<into), (put<on)/sweats, (suit<sweat)')
				(cond
					((== egoState WEARING_SWEATS)
						(Print "You're already wearing the sweatsuit.")
					)
					((TstFlg wetBody)
						(Print "You can't change into the sweatsuit
							with water all over your body!")
					)
					((not nearLocker)
						(Print "You can't reach your clothes from here!")
					)
					((not lockerOpen)
						(Print "Your locker is still closed.")
					)
					((TstFlg lockerRippedOff)
						(Print "Your locker is empty!
							You didn't leave it open when you left before, did you?")
					)
					(else
						(Format @string "Gosh! Don't you look sweet in a pair
							of (newly-larger) women's sweats!")
						(= egoState WEARING_SWEATS)
						(if (not (TstFlg scoredSweats))
							(SetFlg scoredSweats)
							(theGame changeScore 4)
						)
						(self changeState beginStrip)
					)
				)
			)

			((or	(Said '(look<for), find/locker, 69')
					(Said '(look<for), find//locker, 69'))
				(= howFar (GetDistance (ego x) (ego y) LOCKER_LOCATION))
				(cond
					(( > howFar 150)
						(Print "You're freezing; not even close.")
					)
					(( > howFar 80)
						(Print "You're cold.")
					)
					(warm
						(Print "You're warm.")
					)
					(warmer
						(Print "You're getting warmer.")
					)
					(hot
						(Print "You're hot; very hot! You're burning up!!")
					)
					(nearLocker
						(Print "HEY! This is it! You've found good ol' number 69!")
					)
					(else
						(Print "It must be in here somewhere.")
					)
				)
			)

			((Said 'pick/lock, locker, 69')
				(Print "You feel fortunate when you can successfully pick
					your nose!")
			)

			((Said 'feel/locker, top, bay')
				(Print "It feels rusty.")
			)

			((Said '(look<inside), search, (look<in)/locker, (door<locker)')
				(cond
					((not nearLocker)
						(Print "It's difficult to see through those
							narrow slots.")
					)
					((not lockerOpen)
						(Print "It's difficult to see through the
							narrow slots of your closed locker door.")
					)
					((TstFlg lockerRippedOff)
						(Print "It's empty!")
						(Print "Where's your stuff?")
						(Print "You didn't leave the area without locking your 
							locker, did you?")
					)
					((== egoState WEARING_SUIT)
						(Print "There's a photo of a scantily-clad Tom Selleck,
							some spray deodorant,
							and a set of woman's sweats.")
					)
					((== egoState WEARING_TOWEL)
						(Print "There's a photo of a scantily-clad Tom Selleck,
							some spray deodorant,
							your leisure suit,
							and a set of woman's sweats.")
					)
					((== egoState WEARING_SWEATS)
						(Print "There's a photo of a scantily-clad Tom Selleck,
							some spray deodorant,
							and your leisure suit.")
					)
					((or	(== egoState NAKED)
							(== egoState NAKED_BUT_MODEST))
						(Print "There's a photo of a scantily-clad Tom Selleck,
							some spray deodorant,
							your leisure suit,
							and a set of woman's sweats.")
					)
				)
			)

			((Said 'get, spray, wear, use/can, spray, deodorant')
				(cond
					((not nearLocker)
						(Print "You can't reach the deodorant from here.")
					)
					((not lockerOpen)
						(Print "Your locker is still closed.")
					)
					((or	(== egoState NAKED)
						 	(== egoState NAKED_BUT_MODEST)
						 	(== egoState WEARING_TOWEL))
						(Print "Pssssssst.")
						(Print "Pssssssst.")
						(ClrFlg needsDeodorant)
						(if (not (TstFlg sprayedDeodorant))
							(SetFlg sprayedDeodorant)
							(theGame changeScore 27)
						)
						(Print "You spray on some deodorant,
							then return the can to the locker.")
					)
					(else
						(Print "(Does it really help to spray that stuff on
							your clothes?)" LOW)
						(theGame changeScore -1)
					)
				)
			)

			((or	(Said 'wipe, dry[/body, me]')
					(Said 'rub/self, me')
					(Said 'dry')
					(Said 'use, (wipe<with), (dry<off)/towel'))
				(cond
					((not (ego has iTowel))
						(Print "You have no towel.")
					)
					((and	(!= egoState WEARING_TOWEL)
							(!= egoState NAKED_BUT_MODEST)
							(!= egoState NAKED))
						(Print "Now?")
					)
					((not (TstFlg wetBody))
						(Print "You're not wet.")
					)
					((and (!= egoState WEARING_TOWEL)
							(not lockerOpen))
						(Print "Didn't you leave your towel inside the locker?")
					)
					(else
						(ClrFlg wetBody)
						(if (not (TstFlg scoredTowel))
							(SetFlg scoredTowel)
							(theGame changeScore 22)
						)
						(Print "Good idea. 
							You carefully dry every tiny pore to prevent chafing!"
								#icon iTowel 0 0
						)
					)
				)
			)

			((Said 'talk')
				(Print "You are all alone in the locker room,
					but you can hear severe grunting coming from the weight room.")
			)

			((or	(Said '/combination')
					(Said 'is<where, what/combination, locker'))
				(Print "Yes. That's the puzzle, isn't it?!")
			)

			((Said 'look>')
				(cond
					((Said '/man')
						(Print "Looking around, you see some naked men.")
					)

					((Said '/door, doorway')
						(cond
							(nearShower
								(Print "You peer through the doorway and see lots of
									naked men!")
							)
							(nearWeightRoom
								(Print "You look through the doorway and see lots of
									men and women exercising on weight machines.")
								(Print "They are all wearing sweatsuits...")
								(Print "...sweaty sweatsuits!")
							)
							(else
								(Print "The left door on the far wall leads
									to the showers.")
								(Print "The right door leads to the weight room.")
							)
						)
					)

					((and lockerOpen
							(Said '/photo'))
						(Print "In your opinion, he looks a lot like you.")
					)

					((Said '/number')
						(if (or 	nearLocker
									hot)
							(Print "Hey! There's locker #69 over there!!")
						else
							(Printf "The locker nearest you might be #%d."
								(Random 1 999))
							(Print "(Then again, it might not.)" LOW)
						)
					)

					((Said '/69, (locker<69)')
						(Print "It's over there in that skinny little corner
							of the lockers.  You can just walk to it from here.")
					)

					((Said '/locker<my')
						(Print "Yes. That's the puzzle, isn't it?!")
					)

					((Said '/locker, bay')
						(Print "The lockers are made of steel,
							painted in a lovely cyan hue.")
					)

					((Said '/sweatsuit')
						(if (!= egoState WEARING_SWEATS)
							(DontHave)
						else
							(Print "These sweats are so bulky,
								you can't tell what shape your body's in!")
						)
					)

					((Said '/deodorant, can, spray')
						(Print "Perhaps you should use it!")
					)

					((Said '[/room]')
						(Print "You are in the \"Fat City\" spa's locker room.
							On the far wall are two doorways.
							There is another door that leads to the lobby.
							Identical lockers are everywhere.
							Which one should you use?")
					)
				)
			)

			((or	(Said '/69')
					(Said '//69'))
				(Print "There you go again!")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(beginStrip
				(HandsOff)
				(ego
					illegalBits		0,
					setPri,
					setLoop			facingRight,
					setMotion		MoveTo (ego x?) (+ (ego y?) 20)  self,
				)
			)

			(smallDelay
				(delay 2)
			)

			(goBackUp
				(ego
					setMotion		MoveTo (ego x?) (- (ego y?) 20),
					view				(switch egoState
						(NAKED_BUT_MODEST	(+ vLnakedModest	egoIsHunk))
						(NAKED				(+ vLnaked		 	egoIsHunk))
						(WEARING_TOWEL		(+ vLtowel			egoIsHunk))
						(WEARING_SWEATS 	(+ vLsweats			egoIsHunk))
						(else					(+ vLarry			egoIsHunk))
					)
				)
				(= cycles 22)
			)

			(stripDone
				(Print @string)					;**	it's a local.
				(NormalEgo facingRight (ego view))
			)
		)
	)
)


(instance aLocker of View
	(properties
		view					vLockerRoom
		x						1111
		y						1111
		loop					lLocker
	)
)


(instance aMan1 of Actor
	(properties
		view					vLockerRoom
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			posn				(Random 80 200) 14,
			setStep			1 1,
			setCycle			Walk,
			setScript		Man1Script,
		)
	)
)
(instance Man1Script of Script
	(method (changeState newState)
		(switch (= state newState)

			(Man1Init
				(delay (Random 3 6))
			)

			(Man1Start
				(aMan1
					setMotion		MoveTo (Random 81 200) 14 self,
				)
			)

			(Man1Delay
				(delay (Random 6 12))
			)

			(Man1Done
				(aMan1
					setMotion		MoveTo (Random 80 100) 14 self,
				)
				(= state (- Man1Init 1))
			)
		)
	)
)
(instance aMan2 of Actor
	(properties
		view					vLockerRoom
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			posn				(Random -60 1) 8,
			setStep			1 1,
			setCycle			Walk,
			setScript		Man2Script,
		)
	)
)
(instance Man2Script of Script
	(method (changeState newState)
		(switch (= state newState)

			(Man2Init
				(delay (Random 3 6))
			)

			(Man2Start
				(aMan2
					setMotion		MoveTo (Random 2 40) 8 self,
				)
			)

			(Man2Delay
				(delay (Random 6 12))
			)

			(Man2Done
				(aMan2
					setMotion		MoveTo (Random -60 1) 8 self,
				)
				(= state (- Man2Init 1))
			)
		)
	)
)

(instance aMan3 of Actor
	(properties
		view					vLockerRoom
	)

	(method (init)
		(super init)
		(self
			ignoreHorizon,
			ignoreActors,
			illegalBits		0,
			posn				(Random -60 0) 20,
			setStep			1 1,
			setCycle			Walk,
			setScript		Man3Script,
		)
	)
)
(instance Man3Script of Script
	(method (changeState newState)
		(switch (= state newState)

			(Man3Init
				(delay (Random 3 6))
			)

			(Man3Start
				(aMan3
					setMotion		MoveTo (Random 2 22) 20 self,
				)
			)

			(Man3Delay
				(delay (Random 6 12))
			)

			(Man3Done
				(aMan3
					setMotion		MoveTo (Random -60 1) 20 self,
				)
				(= state (- Man3Init 1))
			)
		)
	)
)
